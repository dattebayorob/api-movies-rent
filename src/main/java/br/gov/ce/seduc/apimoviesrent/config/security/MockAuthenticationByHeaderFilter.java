package br.gov.ce.seduc.apimoviesrent.config.security;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.gov.ce.seduc.apimoviesrent.model.entities.User;
import br.gov.ce.seduc.apimoviesrent.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MockAuthenticationByHeaderFilter extends OncePerRequestFilter{
	
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username = request.getHeader("x-username");
		if ( StringUtils.hasText(username) ) {
			User user = userService.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));
			UserDetails userDetails = user.toUserDetals();
            UsernamePasswordAuthenticationToken authentication = 
            	new UsernamePasswordAuthenticationToken( userDetails ,null, userDetails.getAuthorities() );
            authentication.setDetails(user);
            getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}
