package com.example.demo.exceptionGlobla;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.entity.ApiResponse;

@RestControllerAdvice // this is a global exception handler
public class GlobalExceptions {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleExceptionNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<String>(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), "ERR_INTERNAL_SERVER"));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<String>(HttpStatus.NOT_FOUND, "User not found", e.getMessage(), "ERR_USER_NOT_FOUND"));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleException(MethodArgumentNotValidException exmethod){
        List<String> errors = exmethod.getBindingResult().getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ApiResponse<List<String>> response = new ApiResponse<List<String>>(HttpStatus.BAD_REQUEST, "Validation error", errors, "ERR_VALIDATION");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<String>(HttpStatus.BAD_REQUEST, "Bad request", e.getMessage(), "ERR_BAD_REQUEST"));
    }
}
