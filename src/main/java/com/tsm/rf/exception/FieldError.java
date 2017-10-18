package com.tsm.rf.exception;

public class FieldError {

    private String message;

    private String field;

    public FieldError(final String message, final String field) {
        this.field = field;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
