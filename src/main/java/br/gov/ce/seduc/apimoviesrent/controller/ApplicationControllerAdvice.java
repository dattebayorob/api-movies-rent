package br.gov.ce.seduc.apimoviesrent.controller;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Set;
import java.util.function.Function;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.gov.ce.seduc.apimoviesrent.model.dtos.ErrorDTO;
import br.gov.ce.seduc.apimoviesrent.model.exceptions.BusinessException;
import br.gov.ce.seduc.apimoviesrent.model.response.ResponseError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ResponseStatus( code = BAD_REQUEST )
	@ExceptionHandler( MethodArgumentNotValidException.class )
	public ResponseError handleMethodArgumentNotValidException( MethodArgumentNotValidException ex ) {
		return new ResponseError( mapBindingResultsToErrors( ex.getBindingResult() ));
	}
	
	@ResponseStatus( code = BAD_REQUEST )
	@ExceptionHandler( BusinessException.class )
	public ResponseError handleBusinessException(BusinessException ex) {
		return new ResponseError(ex.getMessage(), ex.getErrors());
	}
	
	@ResponseStatus( code = BAD_REQUEST )
	@ExceptionHandler( MissingServletRequestParameterException.class )
	public ResponseError handleMissingServletRequestParameterException( MissingServletRequestParameterException ex) {
		return new ResponseError(String.format("Required '%s' parameter '%s' is not present", ex.getParameterType(), ex.getParameterName()));
	}
	
	@ResponseStatus( code = INTERNAL_SERVER_ERROR )
	@ExceptionHandler( RuntimeException.class )
	public ResponseError handleRuntimeException( RuntimeException ex ) {
		log.error("{}", ex);
		return new ResponseError("Unespected Error");
	}
	
	
	private Set<ErrorDTO> mapBindingResultsToErrors( BindingResult bindingResult ) {
		return bindingResult.getFieldErrors().stream().map(mapFieldErrorToErrorDTO()).collect(toSet());
	}
	
	private Function<FieldError, ErrorDTO> mapFieldErrorToErrorDTO() {
		return fieldError -> new ErrorDTO( fieldError.getDefaultMessage(), fieldError.getField() );
	}
}
