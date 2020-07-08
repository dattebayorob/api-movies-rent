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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode( of = { "id" } )
@Table
@Entity( name = "tb_people" )
public class People {
	
	public People(Long id) {
		this.id = id;
	}
	
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
