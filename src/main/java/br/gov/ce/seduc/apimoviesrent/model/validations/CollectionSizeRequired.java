package br.gov.ce.seduc.apimoviesrent.model.validations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint( validatedBy = CollectionSizeRequiredValidator.class)
public @interface CollectionSizeRequired {
	int min() default 0;
	int max() default 0;
	
	String message() default "Invalid number of items, min {min} max {max}";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
 
}
