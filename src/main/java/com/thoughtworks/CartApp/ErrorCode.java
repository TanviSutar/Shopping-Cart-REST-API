package com.thoughtworks.CartApp;

public enum ErrorCode {
    ITEM_ALREADY_EXISTS(0),
    ITEM_NOT_FOUND(1),
    UNKNOWN_EXCEPTION(2),
    INVALID_ITEM_NAME(3);

    private final int errorVal;
    ErrorCode(int errorVal){
        this.errorVal = errorVal;
    }
}
