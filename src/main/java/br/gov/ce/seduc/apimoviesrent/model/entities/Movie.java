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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Movie {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private String name;
	@ManyToOne( fetch = LAZY )
	@JoinColumn( name = "director_id" )
	private People director;
	@ManyToOne( fetch = LAZY ) 
	@JoinColumn( name = "screenwriter_id" )
	private People screenwriter;
	@ManyToMany
	@JoinTable( 
		name = "movie_cast", 
		joinColumns = @JoinColumn( name = "movie_id" ), 
		inverseJoinColumns = @JoinColumn( name = "cast_id" )
	)
	private Set<People> castings;
	
	@OneToMany( mappedBy = "id.movieId" )
	private Set<MovieRent> rents;
	private Integer quantity;
	
}
