package br.gov.ce.seduc.apimoviesrent.model.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( of = { "id" })
@AllArgsConstructor
public class LabelDTO {
	private Long id;
	private String name;
}
