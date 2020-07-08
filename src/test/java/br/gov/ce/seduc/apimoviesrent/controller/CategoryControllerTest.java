package br.gov.ce.seduc.apimoviesrent.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.service.CategoryService;
import br.gov.ce.seduc.apimoviesrent.util.MockMvcBuilder;

@ExtendWith( MockitoExtension.class )
class CategoryControllerTest {

	@Mock CategoryService categoryService;
	
	MockMvc mvc;
	
	@BeforeEach
	public void beforeEach() {
		mvc = MockMvcBuilder.standaloneSetup( new CategoryController(categoryService));
	}
	
	@Test
	public void shoulRetrieveAllCAtegories() throws Exception {
		
		when( categoryService.findAll() ).thenReturn(new HashSet<>(Arrays.asList(
			new LabelDTO(1l, "Action"), new LabelDTO(2l, "Drama")
		)));
		
		mvc.perform(get("/categories"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.hasSize(2)));
	}

}
