package br.gov.ce.seduc.apimoviesrent.config.security;

import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

	@Override
	public Session activeSession() {
		return new Session(1l, "dattebayoRob");
	}

}
