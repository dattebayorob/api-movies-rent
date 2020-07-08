package br.gov.ce.seduc.apimoviesrent.model.mappers.impl;

import org.springframework.stereotype.Component;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Category;
import br.gov.ce.seduc.apimoviesrent.model.mappers.CategoryMapper;

@Component
public class CategoryMapperImpl implements CategoryMapper{

	@Override
	public LabelDTO toLabelDTO(Category category) {
		return new LabelDTO(category.getId(), category.getName());
	}

}
