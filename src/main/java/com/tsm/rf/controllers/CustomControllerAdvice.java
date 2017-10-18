package com.tsm.rf.controllers;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tsm.rf.exception.BadRequestException;
import com.tsm.rf.exception.FieldError;
import com.tsm.rf.exception.ResourceNotFoundException;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ResponseEntity<List<FieldError>> handleConstraintViolationException(
			ConstraintViolationException ex) {
		List<FieldError> response = ex.getConstraintViolations().stream().map(generateFieldErrorFunction)
				.collect(Collectors.toList());
		return new ResponseEntity<List<FieldError>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ResponseEntity<FieldError> handleNotFoundMessageException(
			final ResourceNotFoundException exception) {
		String messageError = resolveLocalizedMessage(exception.getErrorCode());
		return new ResponseEntity<FieldError>(new FieldError(messageError, ""), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ResponseEntity<FieldError> handleBadRequestMessageException(
			final BadRequestException exception) {
		String messageError = resolveLocalizedMessage(exception.getErrorCode());
		return new ResponseEntity<FieldError>(new FieldError(messageError, ""), HttpStatus.BAD_REQUEST);
	}

	private String resolveLocalizedMessage(final String key, final Object... args) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, args, "General Error", currentLocale);
	}

	private Function<ConstraintViolation<?>, FieldError> generateFieldErrorFunction = (c) -> {
		String errorMessage = resolveLocalizedMessage(c.getMessageTemplate());
		return new FieldError(errorMessage, c.getPropertyPath().toString());
	};

}
