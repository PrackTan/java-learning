package com.example.demo.exceptionGlobla;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.entity.ApiResponse;

@RestControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleExceptionNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<String>(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<String>(HttpStatus.NOT_FOUND, "User not found", e.getMessage(), null));
    }
}
