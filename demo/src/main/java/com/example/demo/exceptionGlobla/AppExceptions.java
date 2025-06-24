package com.example.demo.exceptionGlobla;

public class AppExceptions extends RuntimeException {
    private ErrorCode errorCode;
    public AppExceptions(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
