package br.gov.ce.seduc.apimoviesrent.model.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollectionSizeRequiredValidatorTest {

	@Mock List<String> mocks;
	@Mock ConstraintValidatorContext context;
	@Mock CollectionSizeRequired collectionSizeRequired;
	
	CollectionSizeRequiredValidator validator;
	
	@BeforeEach
	public void beforeEach() {
		validator = new CollectionSizeRequiredValidator();
	}
	
	@Test
	void shouldReturnValidIfNoMinAndMaxIsInformed() {
		validator.initialize( collectionSizeRequired );
		assertTrue(validator.isValid(mocks, context));
		verify(mocks, never()).size();
	}
	
	@Test
	void shouldReturnInvalidIfMinIsPassedAndCollectionHasInferiorSize() {
		when(mocks.size()).thenReturn(3);
		when(collectionSizeRequired.min()).thenReturn(4);
		validator.initialize( collectionSizeRequired );
		assertFalse( validator.isValid( mocks, context ) );
	}
	
	@Test
	void shouldReturnInvalidIfMaxIsPassedAndCollectionHasSuperiorSize() {
		when(mocks.size()).thenReturn(3);
		when(collectionSizeRequired.max()).thenReturn(2);
		validator.initialize( collectionSizeRequired );
		assertFalse( validator.isValid( mocks, context ) );
	}
	
	@Test
	void shouldReturnInvalidIfMaxAndMinAreInformedAndCollectionHasSizeInferiorAMinAndSuperiorAMax() {
		when(collectionSizeRequired.min()).thenReturn(2);
		when(collectionSizeRequired.max()).thenReturn(5);
		when(mocks.size()).thenReturn(6);
		
		validator.initialize( collectionSizeRequired );
		assertFalse( validator.isValid( mocks, context ) );
		
		validator.initialize( collectionSizeRequired );
		when(mocks.size()).thenReturn(1);
		assertFalse( validator.isValid( mocks, context ) );
	}

}
