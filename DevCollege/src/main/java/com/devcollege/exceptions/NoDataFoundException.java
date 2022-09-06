package com.devcollege.exceptions;

public class NoDataFoundException extends RuntimeException{

    String message;

    @Override
    public String toString() {
        return "" +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NoDataFoundException(String message) {
        super(String.format("%s not found ", message));
        this.message = message;
    }
}
