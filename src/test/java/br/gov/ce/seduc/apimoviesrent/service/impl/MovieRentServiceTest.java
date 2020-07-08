package br.gov.ce.seduc.apimoviesrent.service.impl;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ce.seduc.apimoviesrent.model.entities.MovieRent;
import br.gov.ce.seduc.apimoviesrent.model.entities.pk.MovieRentPK;
import br.gov.ce.seduc.apimoviesrent.model.repositories.MovieRentRepository;

@ExtendWith( MockitoExtension.class)
@DisplayName(" Movie Rent Service tests")
class MovieRentServiceTest {

	static final Long MOVIE_ID = 1l;
	static final Long USER_ID = 3l;
	
	@Mock MovieRent movieRent;
	
	@Mock MovieRentRepository movieRentRepository;
	
	MovieRentServiceImpl movieRentService;
	
	@BeforeEach
	public void beforeEach() {
		movieRentService = new MovieRentServiceImpl(movieRentRepository);
	}
	
	@Test
	@DisplayName(" Should return a total of five rented movies ")
	public void shouldReturnATotalOfFiveRentedMovies() {
		
		final Integer expectedTotalOfRented = 5;
		
		when( movieRentRepository.countByIdMovieIdAndReturnDateIsNull(MOVIE_ID) ).thenReturn( expectedTotalOfRented );

		Integer totalOfRented = movieRentService.countRentedByMovie(MOVIE_ID);
		
		assertEquals( expectedTotalOfRented, totalOfRented);
		verify( movieRentRepository, times(1)).countByIdMovieIdAndReturnDateIsNull(MOVIE_ID);
		
	}
	
	@Test
	@DisplayName(" Should return true, the movie is rented by the informed user")
	public void shouldReturnTrueToTheMovieAndUserIds() {
		
		when( movieRentRepository.existesByIdMovieIdAndIdUserIdAndReturnDateIsNull(MOVIE_ID, USER_ID) )
			.thenReturn(TRUE);
		assertTrue( movieRentService.isMovieRentedByUser(MOVIE_ID, USER_ID) );
		verify( movieRentRepository, times(1)).existesByIdMovieIdAndIdUserIdAndReturnDateIsNull(MOVIE_ID, USER_ID);
		
	}
	
	@Test
	@DisplayName(" Should save a movie rent association ")
	public void shouldSaveAMovieRentAssociation() {
		
		final MovieRent expectedMovie = new MovieRent(USER_ID, MOVIE_ID);
		when( movieRentRepository.save(expectedMovie)).thenReturn(expectedMovie);
		
		movieRentService.saveRent(MOVIE_ID, USER_ID);
		
		verify( movieRentRepository, times(1)).save(expectedMovie);
		
	}
	
	@Test
	@DisplayName(" Should set return date when found by id")
	public void shouldSetReturnDateWhenFoundById() {
		
		when( movieRentRepository.findById( Mockito.any( MovieRentPK.class ) )).thenReturn( Optional.of( movieRent) );
		when( movieRentRepository.save( movieRent)).thenReturn( movieRent );
		
		doNothing().when( movieRent ).setReturnDate(Mockito.any(LocalDateTime.class));
		
		movieRentService.returnRent(MOVIE_ID, USER_ID);
				
		InOrder inOrder = Mockito.inOrder( movieRentRepository, movieRent );
		inOrder.verify( movieRentRepository, times(1)).findById( Mockito.any( MovieRentPK.class ) );
		inOrder.verify( movieRent, times(1) ).setReturnDate(Mockito.any(LocalDateTime.class));
		inOrder.verify( movieRentRepository, times(1)).save( movieRent );

		
	}
	

}
