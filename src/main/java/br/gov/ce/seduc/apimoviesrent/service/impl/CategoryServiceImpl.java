package br.gov.ce.seduc.apimoviesrent.service.impl;

import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.springframework.stereotype.Service;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.mappers.CategoryMapper;
import br.gov.ce.seduc.apimoviesrent.model.repositories.CategoryRepository;
import br.gov.ce.seduc.apimoviesrent.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public Set<LabelDTO> findAll() {
		return categoryRepository.findAll().stream()
				.map(categoryMapper::toLabelDTO)
				.sorted(( label1, label2 ) -> label1.getName().compareTo( label2.getName()))
				.collect(toSet());
	}
}
