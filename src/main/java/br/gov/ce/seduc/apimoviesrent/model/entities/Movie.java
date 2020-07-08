package br.gov.ce.seduc.apimoviesrent.model.entities;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
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
@Table( name = "tb_movie" )
public class Movie {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private String name;
	@Column(name = "picture_path")
	private String picturePath;
	
	@ManyToOne( fetch = LAZY )
	@JoinColumn( name = "director_id" )
	private People director;
	@ManyToOne( fetch = LAZY ) 
	@JoinColumn( name = "screenwriter_id" )
	private People screenwriter;
	@ManyToMany
	@JoinTable( 
		name = "tb_movie_cast", 
		joinColumns = @JoinColumn( name = "movie_id" ), 
		inverseJoinColumns = @JoinColumn( name = "cast_id" )
	)
	private Set<People> castings;
	
	@ManyToMany
	@JoinTable( 
		name = "tb_movie_category", 
		joinColumns = @JoinColumn( name = "movie_id" ), 
		inverseJoinColumns = @JoinColumn( name = "category_id" )
	)
	private Set<Category> categories;
	
	@OneToMany( mappedBy = "id.movieId" )
	private Set<MovieRent> rents;
	private Integer quantity;
	
}
