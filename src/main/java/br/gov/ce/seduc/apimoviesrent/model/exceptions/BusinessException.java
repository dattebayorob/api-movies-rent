package br.gov.ce.seduc.apimoviesrent.model.exceptions;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import br.gov.ce.seduc.apimoviesrent.model.dtos.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{
	private static final long serialVersionUID = 4663902431244583916L;
	
	private final Set<ErrorDTO> errors;
	
	public BusinessException(String message, String ... messages) {
		this( message, asList(messages).stream().map(ErrorDTO::new).collect(toList()) );
	}
	
	public BusinessException(String message, Collection<ErrorDTO> errors ) {
		super(message);
		this.errors = new HashSet<>(errors);
	}
	
	
}
