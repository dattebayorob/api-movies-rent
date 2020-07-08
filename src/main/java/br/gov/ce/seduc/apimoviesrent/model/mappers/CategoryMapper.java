package br.gov.ce.seduc.apimoviesrent.model.mappers;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Category;

public interface CategoryMapper {
	public LabelDTO toLabelDTO( Category category );
}
