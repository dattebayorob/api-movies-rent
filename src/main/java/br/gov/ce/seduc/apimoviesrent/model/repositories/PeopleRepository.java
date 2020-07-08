package br.gov.ce.seduc.apimoviesrent.model.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.gov.ce.seduc.apimoviesrent.model.entities.People;

public interface PeopleRepository extends JpaRepository<People, Long>{
	@Modifying
	@Query("from People p where lower(name) like lower(concat('%', :name,'%'))")
	Set<People> findByNameLikeIgnoreCase( String name );
}
