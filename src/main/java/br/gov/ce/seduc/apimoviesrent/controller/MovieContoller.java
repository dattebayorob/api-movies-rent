package br.gov.ce.seduc.apimoviesrent.controller;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.gov.ce.seduc.apimoviesrent.model.dtos.CastDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.service.CastService;
import br.gov.ce.seduc.apimoviesrent.service.MovieService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieContoller {
	
	private final MovieService movieService;
	private final CastService castService;
	
	@GetMapping
	public Page<MovieDTO> getMovies( Pageable pageable ) {
		return movieService.findMovies( pageable );
	}
	
	@GetMapping("{movieId}")
	public MovieDTO getMovie( @PathVariable("movieId") Long movieId) {
		return findById(movieId)
					.orElseThrow( () -> new ResponseStatusException(NOT_FOUND) );
	}
	
	@GetMapping("{movieId}/castings")
	public Set<CastDTO> getCastingsOfMovie( @PathVariable("movieId") Long movieId ) {
		return findById(movieId)
					.map( movie -> castService.findByMovie(movie.getId()) )
					.orElseThrow( () -> new ResponseStatusException(NOT_FOUND) );
	}
	
	private Optional<MovieDTO> findById(Long movieId) {
		return ofNullable(movieId).flatMap( movieService::findById );
	}
	
	
}
