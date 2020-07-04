package br.gov.ce.seduc.apimoviesrent.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;

public interface MovieService {
	public Page<MovieDTO> findMovies(Pageable pageable);

	public Optional<MovieDTO> findById(Long movieId);
}
