package br.gov.ce.seduc.apimoviesrent.model.dtos;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.emptySet;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.gov.ce.seduc.apimoviesrent.model.validations.CollectionSizeRequired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class MovieDTO {
	private Long id;
	@NotEmpty( message = "The movie name must be informed" )
	private String name;
	@Default
	@CollectionSizeRequired( min = 1, message = "At least one category must be informed" )
	private Set<String> categories = emptySet();
	@NotNull( message = "A director must be informed" )
	private LabelDTO director;
	@NotNull( message = "A Screen Writer must be informed" )
	private LabelDTO screenwriter;
	private Set<CastDTO> castings;
	private boolean rented;
	
}
