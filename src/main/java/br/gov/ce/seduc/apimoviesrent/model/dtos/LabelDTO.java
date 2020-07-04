package br.gov.ce.seduc.apimoviesrent.model.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( of = { "id" })
public class LabelDTO {
	private Long id;
	private String name;
}
