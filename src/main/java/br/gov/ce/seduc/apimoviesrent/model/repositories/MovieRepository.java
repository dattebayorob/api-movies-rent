package br.gov.ce.seduc.apimoviesrent.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.seduc.apimoviesrent.model.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}
