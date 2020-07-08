package br.gov.ce.seduc.apimoviesrent.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;

public interface MovieService {
	public Page<MovieDTO> findMovies(Pageable pageable, Long userId);
	public Optional<MovieDTO> findById(Long movieId, Long userId);
	public Optional<MovieDTO> save(MovieDTO movie);
	public Optional<MovieDTO> update(MovieDTO movie);
	public Optional<MovieDTO> rentMovie( Long movieId, Long userId );
	public Optional<MovieDTO> returnMovie( Long movieId, Long userId );
}
