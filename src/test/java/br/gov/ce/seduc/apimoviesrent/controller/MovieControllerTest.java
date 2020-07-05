package br.gov.ce.seduc.apimoviesrent.controller;

import static br.gov.ce.seduc.apimoviesrent.model.constants.Endpoints.MOVIES;
import static br.gov.ce.seduc.apimoviesrent.util.MockMvcBuilder.standaloneSetup;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.seduc.apimoviesrent.config.security.AuthService;
import br.gov.ce.seduc.apimoviesrent.config.security.Session;
import br.gov.ce.seduc.apimoviesrent.model.dtos.CastDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.model.exceptions.BusinessException;
import br.gov.ce.seduc.apimoviesrent.service.CastService;
import br.gov.ce.seduc.apimoviesrent.service.MovieService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Movies Resource unit tests")
public class MovieControllerTest {
	
	static String MOVIE_NOT_AVAILABLE = "Movie isnt available for rent";
	
	static Long MOVIE_ID = 1l;
	static String MOVIE_NAME = "Parasite";
	static String MOVIE_CATEGORY_THRILLER = "Thriller";
	static String MOVIE_DIRECTOR_NAME = "Bong joon-ho";
	static LabelDTO DIRECTOR_AND_WRITER = new LabelDTO(1l, MOVIE_DIRECTOR_NAME);
		
	@Mock MovieService movieService;
	@Mock CastService castService;
	@Mock AuthService authService;
	
	MockMvc mockMvc;
	
	Pageable pageable;
	
	@BeforeEach
	public void beforeEach() {
		mockMvc = standaloneSetup( 
			new MovieContoller( movieService, castService, authService ) 
		);
	}
	
	@Test
	@DisplayName("Should returna paginated collection of movies by filters with HttpStatus 200")
	public void shouldReturnAPaginatedCollectionOfMoviesWithStatusOk() throws Exception {
		pageable = PageRequest.of(0, 20);
		MovieDTO movie = MovieDTO.builder().id(1l).build();
		List<MovieDTO> movies = asList(movie);
		
		when(movieService.findMovies(pageable)).thenReturn(
			new PageImpl<>(movies, pageable, pageable.getPageSize())
		);
		
		mockMvc
			.perform(get(MOVIES))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size", is(20)));
	}
	
	@Test
	@DisplayName("Should return a empty page with HttpStatus 200  if there is no movies for the informed filters")
	public void shouldReturnAEmptyPageWIthStatusOk() throws Exception {
		pageable = PageRequest.of(0, 20);
		List<MovieDTO> movies = emptyList();
		
		when(movieService.findMovies(pageable)).thenReturn( new PageImpl<>(movies) );
		
		mockMvc
			.perform(get(MOVIES))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(0)));
		
	}
	
	@Test
	@DisplayName("Shoud retrieve a movie by its id")
	public void shouldRetrieveAMovieByItsId() throws Exception {
		MovieDTO movie = 
			MovieDTO
				.builder()
					.id(MOVIE_ID)
					.name("Parasite")
					.director(DIRECTOR_AND_WRITER)
					.screenwriter(DIRECTOR_AND_WRITER)
				.build();
		
		when(movieService.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
		
		mockMvc
			.perform(get( format("%s/%d", MOVIES, MOVIE_ID) ))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.director.name", is(MOVIE_DIRECTOR_NAME)))
			.andExpect(jsonPath("$.screenwriter.name", is(MOVIE_DIRECTOR_NAME)));
		
	}
	
	@Test
	@DisplayName("Should return a not found status if no movie is found for the Id")
	public void shouldReturnNotFound() throws Exception {
		when(movieService.findById(MOVIE_ID)).thenReturn(Optional.empty());

		mockMvc
			.perform(get( format("%s/%d", MOVIES, MOVIE_ID) ))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Should return bad request if no category is selected when registering a movie")
	public void shouldReturnBadRequestIfNoCategoryIsSelected() throws JsonProcessingException, Exception {
		MovieDTO movie = 
			MovieDTO.builder()
				.name(MOVIE_NAME)
				.director(DIRECTOR_AND_WRITER)
				.screenwriter(DIRECTOR_AND_WRITER)
			.build();
		
		mockMvc.perform(
			post(MOVIES).contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(movie))
		).andExpect(status().isBadRequest());
		
		verify(movieService, never()).save(movie);
	}
	
	@Test
	@DisplayName("Should return bad request if the movie is already rented")
	public void shouldReturnBadRequestIfMovieIsAlreadyRented() throws JsonProcessingException, Exception {
		
		final Session session = Session.builder().id(1l).username("dattebayoRob").build();
		
		when( authService.activeSession() ).thenReturn( session );
		doThrow(new BusinessException(MOVIE_NOT_AVAILABLE)).when( movieService ).rentMovie( MOVIE_ID, session.getId() );
		
		mockMvc
			.perform( 
				patch( format( "%s/%d/rent", MOVIES, MOVIE_ID ) )
			)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is(MOVIE_NOT_AVAILABLE)));
		
		InOrder inOrder = inOrder(movieService, authService);
		
		inOrder.verify(authService, times(1)).activeSession();
		inOrder.verify(movieService, times(1)).rentMovie(MOVIE_ID, session.getId());		
	}
	
	@Test
	@DisplayName("Should insert a movie if all bean validation pass and no business exception is thrown")
	public void shouldInsertAMovie() throws JsonProcessingException, Exception {
		final MovieDTO movie = validMovie();
		
		when(movieService.save(Mockito.any(MovieDTO.class))).thenAnswer( 
		  invocation -> {
			  MovieDTO movieRequestedToSave = invocation.getArgument(0);
			  movieRequestedToSave.setId(MOVIE_ID);
			  return Optional.of(movieRequestedToSave);
		  }
		);
		
		mockMvc.perform(
			post(MOVIES).contentType(APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(movie))
		)
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(jsonPath("$.id", is(MOVIE_ID.intValue())));
		
		verify(movieService, times(1)).save(Mockito.any(MovieDTO.class));
	}
	
	
	@Test
	@DisplayName("Should Return All Cast Of a Movie")
	public void shouldReturnAllCastOfMovie() throws Exception {
		MovieDTO movie = MovieDTO.builder().id(MOVIE_ID).build();
		Set<CastDTO> castings = new HashSet<>(
			Arrays.asList( new CastDTO(1l, "Brad Peet"), new CastDTO(2l, "Tom Cruize") )
		);
		
		when(movieService.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
		when(castService.findByMovie(MOVIE_ID)).thenReturn(castings);
		
		mockMvc
			.perform(get( format("%s/%d/castings", MOVIES, MOVIE_ID) ))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)));
		
		InOrder inOrder = inOrder(castService, movieService);
		inOrder.verify(movieService, times(1)).findById(MOVIE_ID);
		inOrder.verify(castService, times(1)).findByMovie(MOVIE_ID);
		
	}
	
	@Test
	@DisplayName("Should return status 404 when retrieving castings of a movie if movie is not found by id")
	public void shouldReturnStatusNotFoundWhenRetrievingCastingsAndMovieIsNotFound() throws Exception {
		when(movieService.findById(MOVIE_ID)).thenReturn(Optional.empty());
		
		mockMvc
		.perform(get( format("%s/%d/castings", MOVIES, MOVIE_ID) ))
		.andExpect(status().isNotFound());
		
		InOrder inOrder = inOrder(castService, movieService);
		inOrder.verify(movieService, times(1)).findById(MOVIE_ID);
		inOrder.verify(castService, never()).findByMovie(MOVIE_ID);
		
	}
	
	@Test
	@DisplayName("Should return accepted when returning a movie")
	public void shouldReturnStatusAcceptedWhenReturningAMovie() throws Exception {
		
		final Long userId = 3l;
		
		when(movieService.returnMovie(MOVIE_ID, userId)).thenReturn(Optional.of(validMovie()));
				
		mockMvc
			.perform( 
				delete( format( "%s/%d/rent", MOVIES, MOVIE_ID ) ).param("userId", userId.toString()) 
			)
			.andExpect(status().isAccepted());
		
		verify( movieService, times(1) ).returnMovie(MOVIE_ID, userId);
	}
	
	MovieDTO validMovie() {
		return MovieDTO.builder()
				.name(MOVIE_NAME)
				.director(DIRECTOR_AND_WRITER)
				.screenwriter(DIRECTOR_AND_WRITER)
				.categories(new HashSet<>(asList(MOVIE_CATEGORY_THRILLER)))
			.build();
	}
	
}
