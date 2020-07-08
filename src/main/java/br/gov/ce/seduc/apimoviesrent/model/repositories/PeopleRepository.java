package br.gov.ce.seduc.apimoviesrent.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.seduc.apimoviesrent.model.entities.People;

public interface PeopleRepository extends JpaRepository<People, Long>{
	
}
