package com.devcollege.exceptions;

public class ApiResponseException extends RuntimeException {

    String message;
    Boolean success;

    public ApiResponseException(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponseException(String message) {
        super(message);
    }

    public ApiResponseException(String message, boolean b) {
    }
}
