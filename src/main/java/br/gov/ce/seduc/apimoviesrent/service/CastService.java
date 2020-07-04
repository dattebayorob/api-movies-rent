package br.gov.ce.seduc.apimoviesrent.service;

import java.util.Set;

import br.gov.ce.seduc.apimoviesrent.model.dtos.CastDTO;

public interface CastService {
	public Set<CastDTO> findByMovie( Long movieId ) ;
}
