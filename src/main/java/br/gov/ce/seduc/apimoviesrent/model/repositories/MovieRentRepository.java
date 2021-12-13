package br.gov.ce.seduc.apimoviesrent.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.seduc.apimoviesrent.model.entities.MovieRent;
import br.gov.ce.seduc.apimoviesrent.model.entities.pk.MovieRentPK;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MovieRentRepository extends JpaRepository<MovieRent, MovieRentPK>{
	@Transactional( readOnly = true )
	Integer countByIdMovieIdAndReturnDateIsNull( Long movieId );
	Optional<MovieRent> findByIdMovieIdAndIdUserId(Long movieId, Long userId);
	boolean existsByIdMovieIdAndIdUserIdAndReturnDateIsNull( Long movieId, Long userId );
}
