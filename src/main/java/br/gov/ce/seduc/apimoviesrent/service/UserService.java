package br.gov.ce.seduc.apimoviesrent.service;

import java.util.Optional;

import br.gov.ce.seduc.apimoviesrent.model.entities.User;

public interface UserService {
	Optional<User> findByUsername( String username );
}
