package br.gov.ce.seduc.apimoviesrent.service.impl;

import static br.gov.ce.seduc.apimoviesrent.util.MockedFunctionalInterface.mockedFunction;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Movie;
import br.gov.ce.seduc.apimoviesrent.model.exceptions.BusinessException;
import br.gov.ce.seduc.apimoviesrent.model.mappers.MovieMapper;
import br.gov.ce.seduc.apimoviesrent.model.repositories.MovieRepository;
import br.gov.ce.seduc.apimoviesrent.service.MovieRentService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Movie service unit tests")
class MovieServiceTest {
	
	static final Long USER_ID = 3l;
	static final Long MOVIE_ID = 1l;

	@Mock MovieRepository movieRepository;
	@Mock MovieMapper movieMapper;
	@Mock MovieRentService movieRentService;
	
	@Mock Pageable pageable;
	@Mock Movie movie;
	@Mock MovieDTO movieDTO;
	
	MovieServiceImpl movieService;
	
	@BeforeEach
	public void beforeEach() {
		movieService = spy(
			new MovieServiceImpl(movieRepository, movieMapper, movieRentService)
		);
	}
	
	@Test
	@DisplayName("Should return movie with rented by user seted to true if a unit of this movie is already rented by the requesting user")
	public void shouldReturnIsRentedTrue() {
		
		commonRentedByUserTest(TRUE, movieDTO -> assertTrue( movieDTO.isRented() ));
		
	}
	
	@Test
	@DisplayName("Should return movie with rented by user seted to false if the user doesnt have an open rent ")
	public void shouldReturnIsRentedFalse() {
		
		commonRentedByUserTest(FALSE, movieDTO -> assertFalse( movieDTO.isRented() ));
		
	}
	
	@Test
	@DisplayName("Should return movie with availableForRent true if total is greater then the total of rented ")
	public void shouldReturnMovieAvailableForRent() {
		
		final Integer totalUnits = 5;
		
		commonMovieAvailabilityTest(totalUnits, movieResult -> assertTrue(
			movieResult.filter(MovieDTO::isAvailableForRent).isPresent()
		));
		
	}
	
	@Test
	@DisplayName("Should return movie with availableForRent false if total of rented is equal to total of units")
	public void shouldReturnMovieNotAvailableForRent() {
		
		final Integer totalUnits = 4;
		
		commonMovieAvailabilityTest( totalUnits, movieResult -> assertTrue(
			movieResult.filter(MovieDTO::isAvailableForRent).isEmpty()
		));
		
	}
	
	@Test
	@DisplayName(" Should throw business exception if total is less than total of rented")
	public void shouldThrowExceptionIfTotalIsLessThanRented() {
				
		final Integer totalUnits = 5;
		final Integer totalRented = 5;
		when( movie.getId() ).thenReturn( MOVIE_ID );
		when( movieDTO.getId() ).thenReturn( MOVIE_ID );
		
		when( movie.getQuantity() ).thenReturn( totalUnits );
		when( movieDTO.getQuantity() ).thenReturn( 4 );
		
		when( movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
		when( movieRentService.countRentedByMovie(MOVIE_ID)).thenReturn( totalRented );
				
		assertThatThrownBy( () -> movieService.update(movieDTO))
			.isInstanceOfSatisfying(
				BusinessException.class, 
				ex -> assertThat(
					ex.getErrors()).anyMatch( error -> error.getField().equals("quantity")
				)
			);
		verify( movieRentService, times(1) ).countRentedByMovie(MOVIE_ID);
		verify( movieRepository, never() ).save(movie);
	}
	
	@Test
	@DisplayName("Should update movie without validation errors") 
	public void shouldUpdateMovieWIthoutValidationErrors() {
		
		final Integer totalUnits = 5;
		final Integer totalRented = 3;
		when( movie.getId() ).thenReturn( MOVIE_ID );
		when( movieDTO.getId() ).thenReturn( MOVIE_ID );
		
		when( movie.getQuantity() ).thenReturn( totalUnits );
		when( movieDTO.getQuantity() ).thenReturn( 4 );
		
		when( movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
		when( movieRentService.countRentedByMovie(MOVIE_ID)).thenReturn( totalRented );
		when( movieMapper.toMovie(movie, movieDTO)).thenReturn(movie);
		when( movieRepository.save(movie) ).thenReturn(movie);
		when( movieMapper.toMovieDTO(movie)).thenReturn(movieDTO);
		
		assertThatCode( () -> movieService.update(movieDTO)).doesNotThrowAnyException();
		
		InOrder inOrder = Mockito.inOrder( movieRepository, movieRentService, movieMapper );
		
		inOrder.verify( movieRepository, times(1) ).findById(MOVIE_ID);
		inOrder.verify( movieRentService, times(1)).countRentedByMovie(MOVIE_ID);
		inOrder.verify( movieMapper, times(1)).toMovie(movie, movieDTO);
		inOrder.verify( movieRepository, times(1) ).save(movie);
		inOrder.verify( movieMapper, times(1)).toMovieDTO(movie);
		
	}
	
	@Test
	@DisplayName("Should throw exception if movie isnt available for rent")
	public void shouldThrowExceptionIfMovieIsntAvailableForRent() {
		final String errorMessage = "Movie isn't available for rent";
		final Integer totalUnits = 4;
		final Integer totalRented = 4;
		
		when( movie.getId() ).thenReturn( MOVIE_ID );
		when( movie.getQuantity() ).thenReturn( totalUnits );
		
		when( movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
		when( movieRentService.isMovieRentedByUser(MOVIE_ID, USER_ID)).thenReturn(FALSE);
		when( movieRentService.countRentedByMovie(MOVIE_ID)).thenReturn(totalRented);
		
		assertThatThrownBy( () -> movieService.rentMovie(MOVIE_ID, USER_ID))
		.isInstanceOfSatisfying(
			BusinessException.class, 
			ex -> assertThat(
				ex.getErrors()).anyMatch( error -> error.getMessage().equals(errorMessage)
			)
		);
		
		verify( movieRepository, times(1)).findById(MOVIE_ID);
		verify( movieRentService, times(1)).isMovieRentedByUser(MOVIE_ID, USER_ID);
		verify( movieRentService, times(1)).countRentedByMovie(MOVIE_ID);
		verifyNoMoreInteractions( movieRentService );
		
	}
	
	@Test
	@DisplayName("Should return movie")
	public void shouldREturnMovie() {
		when( movie.getId() ).thenReturn(MOVIE_ID);
		when( movieRepository.findById( MOVIE_ID )).thenReturn( Optional.of(movie) );
		doNothing().when( movieRentService ).returnRent(MOVIE_ID, USER_ID);
		doNothing().when( movieDTO ).setRented(FALSE);
		when( movieMapper.toMovieDTO(movie ) ).thenReturn(movieDTO);
		
		Optional<MovieDTO> returnedMovie = movieService.returnMovie(MOVIE_ID, USER_ID);
		
		assertThat(returnedMovie).isNotEmpty();
		
		InOrder inOrder = Mockito.inOrder( movieRentService, movieDTO );
		inOrder.verify( movieRentService, times( 1 )).returnRent(MOVIE_ID, USER_ID);
		inOrder.verify( movieDTO, times(1)).setRented(FALSE);
	}
	
	
	private void commonRentedByUserTest( boolean mockedIsRented, Consumer<MovieDTO> assertion ) {
		final MovieDTO movieDTO = MovieDTO.builder().id(MOVIE_ID).build();
		
		Function<MovieDTO, MovieDTO> mockFunction = movie -> movie;
		
		when( movieRepository.findById( MOVIE_ID )).thenReturn( Optional.of(movie) );
		when( movieMapper.toMovieDTO(movie) ).thenReturn( movieDTO );
		
		when( movieRentService.isMovieRentedByUser( MOVIE_ID, USER_ID) ).thenReturn( mockedIsRented );
		
		doReturn( mockFunction ).when( movieService ).mapAvailableForRent();
		
		Optional<MovieDTO> result = movieService.findById(MOVIE_ID, USER_ID);
		
		assertThat( result ).isNotEmpty().hasValueSatisfying( assertion );
		
		verify( movieRentService, times(1) ).isMovieRentedByUser(MOVIE_ID, USER_ID);
	}
	
	private void commonMovieAvailabilityTest( Integer totalUnits, Consumer<Optional<MovieDTO>> assertion ) {
		
		final Integer totalRented = 4;
		final MovieDTO movieDTO = MovieDTO.builder().id(MOVIE_ID).quantity(totalUnits).build();
		
		when( movieRepository.findById( MOVIE_ID )).thenReturn( Optional.of(movie) );
		when( movieMapper.toMovieDTO( movie )).thenReturn( movieDTO );
		when( movieRentService.countRentedByMovie( MOVIE_ID )).thenReturn( totalRented );
		
		doReturn(mockedFunction()).when( movieService ).mapRentedByUser( USER_ID );
		
		Optional<MovieDTO> result = movieService.findById(MOVIE_ID, USER_ID);
		
		assertThat( result ).isNotEmpty().satisfies( assertion );
		
		verify( movieRentService, times(1)).countRentedByMovie(MOVIE_ID);
	}
	
}
