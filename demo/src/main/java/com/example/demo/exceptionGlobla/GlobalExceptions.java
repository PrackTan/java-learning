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
        // log the exception
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(new ApiResponse<String>(httpStatus, message, message, errorCode.getMessage()));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleException(NoSuchElementException e){
        ErrorCode errorCode = ErrorCode.NOT_FOUND;
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(new ApiResponse<String>(httpStatus, message, message, errorCode.getMessage()));
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
    @ExceptionHandler(AppExceptions.class)
    public ResponseEntity<ApiResponse<?>> handleException(AppExceptions e){
        ErrorCode errorCode = e.getErrorCode();
        String code = String.valueOf(errorCode.getCode());
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.valueOf(errorCode.getCode());
        return ResponseEntity.status(httpStatus).body(new ApiResponse<String>(httpStatus, message, message, code));
    }
}
