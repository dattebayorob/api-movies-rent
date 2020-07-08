package br.gov.ce.seduc.apimoviesrent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.seduc.apimoviesrent.config.security.AuthService;
import br.gov.ce.seduc.apimoviesrent.config.security.Session;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final AuthService authService;
	
	@GetMapping("me")
	public Session activeSession() {
		return authService.activeSession();
	}
}
