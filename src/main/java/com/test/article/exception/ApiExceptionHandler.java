package com.test.article.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Optional: Change if needed
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ApiError.Builder.newBuilder()
                .status(HttpStatus.NOT_FOUND.value())
                .msg(ex.getMessage())
                .date(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<BadRequestError> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error ->
                        errors.add(new BadRequestError(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage(), ((FieldError) error).getField(), LocalDateTime.now()))
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
