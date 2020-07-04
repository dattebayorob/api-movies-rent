package br.gov.ce.seduc.apimoviesrent.model.dtos;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.emptySet;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	private String name;
	@Default
	private Set<String> categories = emptySet();
	private LabelDTO director;
	private LabelDTO screenwriter;
	private Set<CastDTO> castings;
	private boolean rented;
	
}
