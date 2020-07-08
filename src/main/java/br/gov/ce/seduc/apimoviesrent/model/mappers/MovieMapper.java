package br.gov.ce.seduc.apimoviesrent.model.mappers;

import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Movie;

public interface MovieMapper {
	public MovieDTO toMovieDTO( Movie movie );
	public Movie toMovie( MovieDTO movie );
	public Movie toMovie( Movie movie, MovieDTO updatedMovie );
}
