package org.worldcubeassociation.statistics.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(ResponseError
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")))
                        .build());
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
