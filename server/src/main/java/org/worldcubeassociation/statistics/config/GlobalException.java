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
import org.worldcubeassociation.statistics.exception.UnauthorizedException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    private ResponseEntity<ResponseError> buildResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(ResponseError
                .builder()
                .status(status.value())
                .message(message).build(), status);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ResponseError> handleInvalidParameterException(InvalidParameterException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleConstraintViolation(ConstraintViolationException ex) {
        return buildResponse(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handleRuntimeException(RuntimeException ex) {
        return buildResponse("An internal error occurred. Please contact WST.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleRuntimeException(Exception ex) {
        return buildResponse("An internal error occurred. Please contact WST.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @Builder
    private static final class ResponseError {
        private int status;
        private String message;
    }
}
