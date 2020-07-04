package br.gov.ce.seduc.apimoviesrent.config.security;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("session")
@Service
public interface AuthService {
	public Session activeSession();
}
