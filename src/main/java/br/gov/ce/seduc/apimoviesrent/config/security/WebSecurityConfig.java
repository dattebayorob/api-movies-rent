package br.gov.ce.seduc.apimoviesrent.config.security;

import static br.gov.ce.seduc.apimoviesrent.model.constants.Roles.ADMIN;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String MOVIES =  "/movies/**"; 
	
	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests( 
			authorizeRequests -> authorizeRequests
				.antMatchers(HttpMethod.GET, MOVIES).permitAll()
				.antMatchers(HttpMethod.POST, MOVIES).hasRole(ADMIN)
				.antMatchers(HttpMethod.DELETE, MOVIES).hasRole(ADMIN)
				.antMatchers(HttpMethod.PATCH, MOVIES).hasRole(ADMIN)
				.antMatchers(HttpMethod.PATCH, MOVIES).authenticated()
				.anyRequest().denyAll()
		);
	}
	
}
