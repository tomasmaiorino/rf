package com.tsm.rf.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings(value = { "rawtypes" })
public class BaseController {

	protected static final String ADMIN_TOKEN_HEADER = "AT";

	public static final String JSON_VALUE = "application/json";

	protected static final String COMMA_SEPARATOR = ",";

	@Autowired
	private Validator validator;

	protected <T> void validate(final T object, Class clazz) {
		Set<ConstraintViolation<T>> violations = validator.validate(object, clazz);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

}
