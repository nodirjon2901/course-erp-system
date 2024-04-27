package org.example.courseerpsystem.exception;

public class WrongUserLoginException extends RuntimeException {

    public WrongUserLoginException(String message) {
        super(message);
    }
}
