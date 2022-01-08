package com.practice.CartApp;

public class ErrorDTO implements DTO{
    public final ErrorCode errorCode;
    public final String details;

    public ErrorDTO(ErrorCode errorCode, String details){
        this.errorCode = errorCode;
        this.details = details;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }
}
