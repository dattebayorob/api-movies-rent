package br.gov.ce.seduc.apimoviesrent.model.entities.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( of = { "userId", "movieId" } )
@Embeddable
public class MovieRentPK implements Serializable{
	
	private static final long serialVersionUID = 8495897917745871746L;
	
	@Column( name = "user_id" )
	private Long userId;
	@Column( name = "movie_id" )
	private Long movieId;
}
