package br.gov.ce.seduc.apimoviesrent.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gov.ce.seduc.apimoviesrent.model.entities.User;
import br.gov.ce.seduc.apimoviesrent.model.repositories.UserRepository;
import br.gov.ce.seduc.apimoviesrent.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByName(username);
	}

}
