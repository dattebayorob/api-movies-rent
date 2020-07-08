package br.gov.ce.seduc.apimoviesrent.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.seduc.apimoviesrent.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByName(String name);
}
