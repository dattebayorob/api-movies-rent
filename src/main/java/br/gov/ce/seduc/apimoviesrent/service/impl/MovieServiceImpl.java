package br.gov.ce.seduc.apimoviesrent.service.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.gov.ce.seduc.apimoviesrent.model.dtos.ErrorDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Movie;
import br.gov.ce.seduc.apimoviesrent.model.exceptions.BusinessException;
import br.gov.ce.seduc.apimoviesrent.model.mappers.MovieMapper;
import br.gov.ce.seduc.apimoviesrent.model.repositories.MovieRepository;
import br.gov.ce.seduc.apimoviesrent.service.MovieRentService;
import br.gov.ce.seduc.apimoviesrent.service.MovieService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
	
	private final MovieRepository movieRepository;
	private final MovieMapper movieMapper;
	private final MovieRentService movieRentService;

	@Override
	public Page<MovieDTO> findMovies(Pageable pageable, Long userId) {
		return movieRepository
			.findAll(pageable)
			.map( movieMapper::toMovieDTO )
			.map( mapRentedByUser( userId ) )
			.map( mapAvailableForRent() );
	}

	@Override
	public Optional<MovieDTO> findById(Long movieId, Long userId) {
		return movieRepository
			.findById(movieId)
			.map( movieMapper::toMovieDTO )
			.map( mapRentedByUser( userId ) )
			.map( mapAvailableForRent() );
	}

	@Override
	public Optional<MovieDTO> save( MovieDTO movie ) {
		return Optional.of(movie)
					.map( movieMapper::toMovie )
					.map( movieRepository::save )
					.map( movieMapper::toMovieDTO );
	}
	
	@Override
	public Optional<MovieDTO> update( MovieDTO movie ) {
		
		Assert.notNull( movie.getId(), "Id must be informed");
		
		return movieRepository.findById( movie.getId() )
					.map( validateUpdate(movie) )
					.map( mapMovieUpdate(movie) )
					.map( movieRepository::save )
					.map( movieMapper::toMovieDTO );
				
	}

	@Override
	public Optional<MovieDTO> rentMovie( Long movieId, Long userId ) {
		return movieRepository.findById(movieId)
				.map( validateRent( userId ))
				.map( saveRent( userId ))
				.map( mapToMovieDTOAndSetRentedTo( TRUE ));
	}
	
	@Override
	public Optional<MovieDTO> returnMovie( Long movieId, Long userId ) {
		return movieRepository.findById( movieId )
				.map( returnRent( userId ) )
				.map( mapToMovieDTOAndSetRentedTo( FALSE ) );
	}
	
	protected Function<MovieDTO, MovieDTO> mapRentedByUser( Long userId ) {
		return movie -> {
			movie.setRented(
				movieRentService.isMovieRentedByUser( movie.getId(), userId)
			);
			return movie;
		};
	}
	
	protected Function<MovieDTO, MovieDTO> mapAvailableForRent() {
		return movie -> {
			
			if ( movie.isRented() ) return movie;
			
			final Integer quantityRented = movieRentService.countRentedByMovie( movie.getId() );
			movie.setAvailableForRent(
				!movie.getQuantity().equals( quantityRented )
			);
			return movie;
		};
	}
	
	protected Function<Movie, Movie> validateUpdate( MovieDTO updatedMovie ) {
		return movie -> {
			
			Set<ErrorDTO> errors = new HashSet<>();
			
			if ( !movie.getQuantity().equals( updatedMovie.getQuantity() ) ) {
				final Integer totalRented = movieRentService.countRentedByMovie( movie.getId() );
				if ( updatedMovie.getQuantity() < totalRented ) {
					errors.add( new ErrorDTO("Total units of movie is less than the total of rented", "quantity"));
				}
			}
			
			if ( !errors.isEmpty() ) {
				throw new BusinessException("Error updating movie", errors);
			}
			
			return movie;
		};
	}
	
	protected Function<Movie, Movie> mapMovieUpdate( MovieDTO updatedMovie ) {
		return movie -> movieMapper.toMovie(movie, updatedMovie);
	}
	
	protected Function<Movie, Movie> validateRent( Long userId ) {
		return movie -> {
			
			Set<ErrorDTO> errors = new HashSet<>();
			
			if ( !movieRentService.isMovieRentedByUser( movie.getId(), userId ) 
				&& isAllUnitsRented().test( movie )) {
				errors.add(
					new ErrorDTO("Movie isn't available for rent", "rented")
				);
			}
			
			if ( !errors.isEmpty() ) {
				throw new BusinessException("Error renting movie", errors);
			}
			
			return movie;
		};
	}
	
	protected Function<Movie, Movie> saveRent( Long userId ) {
		return movie -> {
			movieRentService.saveRent( movie.getId(), userId );
			return movie;
		};
	}
	
	protected Function<Movie, Movie> returnRent( Long userId ) {
		return movie -> {
			movieRentService.returnRent( movie.getId(), userId );
			return movie;
		};
	}
	
	private Function<Movie, MovieDTO> mapToMovieDTOAndSetRentedTo( boolean rented ) {
		return movie -> {
			final MovieDTO movieDTO = movieMapper.toMovieDTO(movie);
			movieDTO.setRented(rented);
			return movieDTO; 
		};
	}
	
	private Predicate<Movie> isAllUnitsRented() {
		return movie -> {
			final Integer totalRented = 
				movieRentService.countRentedByMovie( movie.getId() );
			return movie.getQuantity().equals( totalRented );
		};
	}

}
