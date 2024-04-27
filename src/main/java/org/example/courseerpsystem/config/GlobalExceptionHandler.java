package org.example.courseerpsystem.config;

import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.exception.WrongUserLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<String> dataNotFoundException(DataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<String> alreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getMessage());
    }

    @ExceptionHandler(value = WrongUserLoginException.class)
    public ResponseEntity<String> wrongUserLoginException(WrongUserLoginException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
