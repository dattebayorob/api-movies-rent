package br.gov.ce.seduc.apimoviesrent.controller;

import static br.gov.ce.seduc.apimoviesrent.model.constants.Endpoints.MOVIES;
import static br.gov.ce.seduc.apimoviesrent.util.MockMvcBuilder.standaloneSetup;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import br.gov.ce.seduc.apimoviesrent.model.dtos.CastDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.service.CastService;
import br.gov.ce.seduc.apimoviesrent.service.MovieService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Movies Resource unit tests")
public class MovieControllerTest {
	
	static long MOVIE_ID = 1l;
	
	@Mock MovieService movieService;
	@Mock CastService castService;
	
	MockMvc mockMvc;
	
	Pageable pageable;
	
	@BeforeEach
	public void beforeEach() {
		mockMvc = standaloneSetup( 
			new MovieContoller( movieService, castService ) 
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
		String bongJoonHo = "Bong joon-ho";
		LabelDTO directorAndWriter = new LabelDTO(1l, bongJoonHo);
		MovieDTO movie = 
			MovieDTO
				.builder()
					.id(MOVIE_ID)
					.name("Parasite")
					.director(directorAndWriter)
					.screenwriter(directorAndWriter)
				.build();
		
		when(movieService.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
		
		mockMvc
			.perform(get( format("%s/%d", MOVIES, MOVIE_ID) ))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.director.name", is(bongJoonHo)))
			.andExpect(jsonPath("$.screenwriter.name", is(bongJoonHo)));
		
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
	
}
