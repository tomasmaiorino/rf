package com.tsm.rf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(final String errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException(final String errorCode, String message) {
        super(message);
    }

}
