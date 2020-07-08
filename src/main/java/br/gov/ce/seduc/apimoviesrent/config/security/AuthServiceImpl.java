package br.gov.ce.seduc.apimoviesrent.config.security;

import static java.util.Optional.ofNullable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.gov.ce.seduc.apimoviesrent.model.entities.User;

@Service
public class AuthServiceImpl implements AuthService{

	@Override
	public Session activeSession() {
		return ofNullable( SecurityContextHolder.getContext().getAuthentication() )
			.map(Authentication::getDetails)
			.filter(details -> details instanceof User)
			.map( user -> (User) user)
			.map( user -> new Session( user.getId(), user.getName() ))
			.orElseGet(Session::new);
	}

}
