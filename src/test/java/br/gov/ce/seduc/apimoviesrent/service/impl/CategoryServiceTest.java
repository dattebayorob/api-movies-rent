package br.gov.ce.seduc.apimoviesrent.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.model.entities.Category;
import br.gov.ce.seduc.apimoviesrent.model.mappers.CategoryMapper;
import br.gov.ce.seduc.apimoviesrent.model.repositories.CategoryRepository;

@ExtendWith( MockitoExtension.class )
class CategoryServiceTest {
	
	@Mock Category category;
	@Mock LabelDTO labelDTO;
	
	@Mock CategoryRepository categoryRepository;
	@Mock CategoryMapper categoryMapper;
	
	CategoryServiceImpl categoryService;
	
	@BeforeEach
	public void beforeEach() {
		categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
	}

	@Test
	void shouldFindAllCategoriesAndMapToLabelDTOSet() {
		
		
		when( categoryRepository.findAll()).thenReturn(Arrays.asList( category ));
		when( categoryMapper.toLabelDTO(category) ).thenReturn(labelDTO);
		
		Set<LabelDTO> categories = categoryService.findAll();
		
		assertThat(categories).isNotEmpty().hasSize(1);
		
		InOrder inOrder = Mockito.inOrder(categoryRepository, categoryMapper);
		inOrder.verify( categoryRepository, times(1)).findAll();
		inOrder.verify( categoryMapper, times(1)).toLabelDTO(category);
	}

}
