package br.gov.ce.seduc.apimoviesrent.model.validations;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CollectionSizeRequiredValidator implements ConstraintValidator<CollectionSizeRequired, Collection<?>>{

	private Integer min;
	private Integer max;
	
	@Override
	public void initialize(CollectionSizeRequired constraintAnnotation) {
		
		if ( constraintAnnotation.min() > 0) this.min = constraintAnnotation.min();
		if ( constraintAnnotation.max() > 0) this.max = constraintAnnotation.max();
		
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	
	@Override
	public boolean isValid(final Collection<?> value, final ConstraintValidatorContext context) {
		if ( min == null && max == null ) return true;
		
		boolean isSizeValid = true;
		
		if ( min != null ) isSizeValid = value.size() >= min;
		if ( max != null ) isSizeValid = isSizeValid && value.size() <= max;
		
		return isSizeValid;
	}

}
