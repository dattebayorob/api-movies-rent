package br.gov.ce.seduc.apimoviesrent.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.gov.ce.seduc.apimoviesrent.model.entities.pk.MovieRentPK;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode( of = { "id" } )
@Entity
@Table( name = "tb_movie_rent" )
public class MovieRent implements Serializable{
	
	private static final long serialVersionUID = -1532217232854719848L;
	
	@EmbeddedId
	private MovieRentPK id;
	@Column( name = "rent_date", updatable = false )
	private LocalDateTime rentDate;
	@Column( name = "return_date")
	private LocalDateTime returnDate;
	
	public MovieRent( Long userId, Long movieId ) {
		this.id = new MovieRentPK( userId, movieId );
		this.rentDate = LocalDateTime.now();
	}
	
}
