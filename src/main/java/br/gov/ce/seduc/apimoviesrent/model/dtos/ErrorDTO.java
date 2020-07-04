package br.gov.ce.seduc.apimoviesrent.model.dtos;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.io.Serializable;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude( value = NON_NULL )
public class ErrorDTO implements Serializable{
	private static final long serialVersionUID = 8106112909450643040L;
	
	private String field;
	private String message;
	
	public ErrorDTO(String message) {
		this(message, null);
	}
	
	public ErrorDTO(String message, @Nullable String field) {
		this.message = message;
		this.field = field;
	}
}
