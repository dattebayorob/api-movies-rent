package br.gov.ce.seduc.apimoviesrent.model.mappers;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.People;

public interface PeopleMapper {
	public LabelDTO toLabelDTO(People people);
}
