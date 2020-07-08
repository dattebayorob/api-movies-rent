package br.gov.ce.seduc.apimoviesrent.model.entities;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Entity
public class People {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	@ManyToMany( fetch = LAZY )
	@JoinTable( 
		name = "movie_cast", 
		joinColumns = @JoinColumn( name = "cast_id" ), 
		inverseJoinColumns = @JoinColumn( name = "movie_id" )
	)
	private Set<Movie> movies;
}
