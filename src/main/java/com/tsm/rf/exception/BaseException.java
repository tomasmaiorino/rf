package com.tsm.rf.exception;

public class BaseException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public BaseException(final String errorCode) {
        this.setErrorCode(errorCode);
    }

    public BaseException(final String errorCode, String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }
}
