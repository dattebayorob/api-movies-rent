package br.gov.ce.seduc.apimoviesrent.model.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.gov.ce.seduc.apimoviesrent.model.dtos.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( value = NON_EMPTY )
public class ResponseError {
	
	public static String DEFAULT_MESSAGE = "Validation Error";
	
	private String message;
	private Set<ErrorDTO> errors;
	
	public ResponseError(String message) {
		this.message = message;
	}
	
	public ResponseError(Collection<ErrorDTO> errors) {
		this.message = DEFAULT_MESSAGE;
		this.errors = new HashSet<>(errors);
	}
}
