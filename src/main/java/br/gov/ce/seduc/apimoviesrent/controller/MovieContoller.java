package br.gov.ce.seduc.apimoviesrent.controller;

import static java.lang.String.format;
import static java.net.URI.create;
import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.unprocessableEntity;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<MovieDTO> getMovie( @PathVariable("movieId") Long movieId) {
		return findById(movieId)
					.map( ResponseEntity::ok )
					.orElseGet( notFound()::build );
	}
	
	@GetMapping("{movieId}/castings")
	public ResponseEntity<Set<CastDTO>> getCastingsOfMovie( @PathVariable("movieId") Long movieId ) {
		return findById(movieId)
					.map( movie -> castService.findByMovie(movie.getId()) )
					.map(ResponseEntity::ok)
					.orElseGet( notFound()::build );
	}
	
	private Optional<MovieDTO> findById(Long movieId) {
		return ofNullable(movieId).flatMap( movieService::findById );
	}
	
	
}
