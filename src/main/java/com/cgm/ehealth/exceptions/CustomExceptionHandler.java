package com.cgm.ehealth.exceptions;

import com.cgm.ehealth.dtos.ErrorResponseDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public ErrorResponseDto onDuplicateKeyException(DuplicateKeyException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto onIllegalArgumentException(IllegalArgumentException exception) {
        String message = exception.getMessage();
        return new ErrorResponseDto(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorResponseDto onEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        String message = ex.getMessage();
        return new ErrorResponseDto(message);
    }
}
