package br.gov.ce.seduc.apimoviesrent.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.seduc.apimoviesrent.model.entities.MovieRent;
import br.gov.ce.seduc.apimoviesrent.model.entities.pk.MovieRentPK;

public interface MovieRentRepository extends JpaRepository<MovieRent, MovieRentPK>{
	@Transactional( readOnly = true )
	Integer countByIdMovieIdAndReturnDateIsNull( Long movieId );
	boolean existesByIdMovieIdAndIdUserIdAndReturnDateIsNull( Long movieId, Long userId );
}
