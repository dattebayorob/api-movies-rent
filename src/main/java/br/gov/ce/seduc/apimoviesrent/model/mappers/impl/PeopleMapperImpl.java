package br.gov.ce.seduc.apimoviesrent.model.mappers.impl;

import org.springframework.stereotype.Component;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.People;
import br.gov.ce.seduc.apimoviesrent.model.mappers.PeopleMapper;

@Component
public class PeopleMapperImpl implements PeopleMapper{

	@Override
	public LabelDTO toLabelDTO(People people) {
		return new LabelDTO( people.getId(), people.getName());
	}

}
