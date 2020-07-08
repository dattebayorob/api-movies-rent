package br.gov.ce.seduc.apimoviesrent.model.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.gov.ce.seduc.apimoviesrent.model.entities.People;

@SpringBootTest
class PeopleRepositoryIT {
	
	@Autowired PeopleRepository peopleRepository;

	@Test
	public void shouldFindByNameLike() {
		
		final String searchCriteria = "Bong";
		
		Set<People> peoples = peopleRepository.findByNameLikeIgnoreCase(searchCriteria);
		
		assertThat(peoples).isNotEmpty();
	}
}
