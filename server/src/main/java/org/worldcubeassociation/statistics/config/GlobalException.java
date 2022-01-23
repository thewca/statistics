package org.worldcubeassociation.statistics.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.exception.NotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundException ex) {
        return new ResponseEntity<>(ResponseError
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ResponseError> genericBadRequest(String message) {
        return ResponseEntity
                .badRequest()
                .body(ResponseError
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build());
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ResponseError> handleInvalidParameterException(InvalidParameterException ex) {
        return genericBadRequest(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleConstraintViolation(ConstraintViolationException ex) {
        return genericBadRequest(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .internalServerError()
                .body(ResponseError
                        .builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("An internal error occurred. Please contact WST.")
                        .build());
    }

    @Data
    @Builder
    private static final class ResponseError {
        private int status;
        private String message;
    }
}
