package com.tsm.rf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(final String errorCode, final String message) {
		super(message);
	}

	public BadRequestException(final String errorCode) {
		super(errorCode);
	}

}
