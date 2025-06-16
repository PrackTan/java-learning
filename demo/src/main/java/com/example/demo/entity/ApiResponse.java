package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T> {
    private T data;
    private String status;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiResponse(HttpStatus httpStatus, String message, T data, String errorCode){
        this.status = httpStatus.is2xxSuccessful() ? "success" : "error";
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

}
