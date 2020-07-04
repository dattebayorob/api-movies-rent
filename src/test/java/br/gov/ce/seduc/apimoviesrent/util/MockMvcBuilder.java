package br.gov.ce.seduc.apimoviesrent.util;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.gov.ce.seduc.apimoviesrent.controller.ApplicationControllerAdvice;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MockMvcBuilder {
	public static MockMvc standaloneSetup( Object ... controllers ) {
		return MockMvcBuilders
					.standaloneSetup(controllers)
						.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
						.setControllerAdvice( new ApplicationControllerAdvice() )
					.build();
	}
}
