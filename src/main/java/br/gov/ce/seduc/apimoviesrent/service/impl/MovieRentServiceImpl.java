package br.gov.ce.seduc.apimoviesrent.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.gov.ce.seduc.apimoviesrent.model.entities.MovieRent;
import br.gov.ce.seduc.apimoviesrent.model.entities.pk.MovieRentPK;
import br.gov.ce.seduc.apimoviesrent.model.repositories.MovieRentRepository;
import br.gov.ce.seduc.apimoviesrent.service.MovieRentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieRentServiceImpl implements MovieRentService{
	
	private final MovieRentRepository repository;

	@Override
	public Integer countRentedByMovie( Long movieId ) {
		return repository.countByIdMovieIdAndReturnDateIsNull( movieId );
	}

	@Override
	public boolean isMovieRentedByUser( Long movieId, Long userId ) {
		return repository.existesByIdMovieIdAndIdUserIdAndReturnDateIsNull( movieId, userId );
	}

	@Override
	public void saveRent(Long movieId, Long userId) {
		repository.save( new MovieRent(userId, movieId) );
	}

	@Override
	public void returnRent(Long movieId, Long userId) {
		repository.findById( new MovieRentPK(movieId, userId) )
			.ifPresent( movieRent -> {
				movieRent.setReturnDate(LocalDateTime.now());
				repository.save(movieRent);
			});
	}

}
