package br.gov.ce.seduc.apimoviesrent.service;

import java.util.Set;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;

public interface CategoryService {
	Set<LabelDTO> findAll();
}
