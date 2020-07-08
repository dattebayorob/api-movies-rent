package br.gov.ce.seduc.apimoviesrent.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
@Entity
@Table( name = "tb_category" )
public class Category implements Serializable{
	private static final long serialVersionUID = 3955621708505840585L;
	
	public Category(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable( 
		name = "tb_movie_category", 
		joinColumns = @JoinColumn( name = "category_id" ), 
		inverseJoinColumns = @JoinColumn( name = "movie_id" )
	)
	private Set<Movie> movies;
	
}
