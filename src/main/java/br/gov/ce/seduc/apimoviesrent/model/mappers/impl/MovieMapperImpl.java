package br.gov.ce.seduc.apimoviesrent.model.mappers.impl;

import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.gov.ce.seduc.apimoviesrent.model.dtos.CastDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.dtos.MovieDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Category;
import br.gov.ce.seduc.apimoviesrent.model.entities.Movie;
import br.gov.ce.seduc.apimoviesrent.model.entities.People;
import br.gov.ce.seduc.apimoviesrent.model.mappers.MovieMapper;

@Component
public class MovieMapperImpl implements MovieMapper{

	@Override
	public MovieDTO toMovieDTO(Movie movie) {
		return MovieDTO
			.builder()
				.id( movie.getId() )
				.name( movie.getName() )
				.director( toLabelDTO( movie.getDirector() ))
				.screenwriter( toLabelDTO( movie.getScreenwriter() ))
				.castings( toCastings(movie.getCastings()) )
				.categories( toCategoriesDTO( movie.getCategories()))
				.quantity( movie.getQuantity() )
			.build();
				
	}

	@Override
	public Movie toMovie(MovieDTO movie) {
		Movie entity = new Movie();
		entity.setId( movie.getId() );
		entity.setName( movie.getName() );
		entity.setDirector( new People( movie.getDirector().getId()) );
		entity.setScreenwriter( new People( movie.getScreenwriter().getId()));
		entity.setCategories( toCategories( movie.getCategories()) );
		entity.setQuantity( movie.getQuantity() );
		return entity;
	}

	@Override
	public Movie toMovie(Movie movie, MovieDTO updatedMovie) {
		Movie updated = toMovie(updatedMovie);
		movie.setDirector( updated.getDirector());
		movie.setScreenwriter( updated.getScreenwriter());
		movie.setName( updated.getName());
		movie.setCategories( updated.getCategories() );
		movie.setQuantity( updated.getQuantity() );
		return movie;
	}
	
	private LabelDTO toLabelDTO( People people ) {
		return Optional.ofNullable(people)
			.map( p -> new LabelDTO( p.getId() , p.getName() ))
			.orElseGet( () -> null );
	}
	
	private Set<CastDTO> toCastings( Set<People> castings ) {
		if ( castings == null || castings.isEmpty() ) return Collections.emptySet();
		return castings.stream()
				.map( cast -> new CastDTO( cast.getId(), cast.getName() ))
				.collect( Collectors.toSet());
	}
	
	private Set<LabelDTO> toCategoriesDTO( Set<Category> categories) {
		return categories.stream()
				.map( category -> new LabelDTO( category.getId(), category.getName()))
				.collect( toSet() );
	}
	
	private Set<Category> toCategories( Set<LabelDTO> categories ) {
		return categories.stream()
				.map( category -> new Category(category.getId()))
				.collect(Collectors.toSet());
	}
}
