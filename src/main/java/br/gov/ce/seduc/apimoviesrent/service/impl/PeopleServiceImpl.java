package br.gov.ce.seduc.apimoviesrent.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.mappers.PeopleMapper;
import br.gov.ce.seduc.apimoviesrent.model.repositories.PeopleRepository;
import br.gov.ce.seduc.apimoviesrent.service.PeopleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService{
	
	private final PeopleRepository peopleRepository;
	private final PeopleMapper peopleMapper;

	@Override
	public Set<LabelDTO> findByNameLike(String name) {
		return peopleRepository.findByNameLikeIgnoreCase(name).stream().map(peopleMapper::toLabelDTO).collect(Collectors.toSet());
	}

}
