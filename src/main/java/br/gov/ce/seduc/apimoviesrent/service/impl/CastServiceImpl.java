package br.gov.ce.seduc.apimoviesrent.service.impl;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.gov.ce.seduc.apimoviesrent.model.dtos.CastDTO;
import br.gov.ce.seduc.apimoviesrent.service.CastService;

@Service
public class CastServiceImpl implements CastService{
	
	@Override
	public Set<CastDTO> findByMovie(Long movieId) {
		// TO DO 
		return Collections.emptySet();
	}

}
