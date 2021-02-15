package io.github.djhaskin987.methuselah.server.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller advice surrounding constraint violations to give back a bad
 * request.
 */
@ControllerAdvice
public class ConstraintViolationExceptionHandler
        extends ResponseEntityExceptionHandler {
    /**
     * Handle constraint violations using 400 BAD REQUEST.
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            final ConstraintViolationException e, final WebRequest request) {
        return handleExceptionInternal(e, (Object) e.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }
}
