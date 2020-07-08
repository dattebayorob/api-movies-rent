package br.gov.ce.seduc.apimoviesrent.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.seduc.apimoviesrent.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
