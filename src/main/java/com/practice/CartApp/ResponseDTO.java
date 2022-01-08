package com.practice.CartApp;

public class ResponseDTO implements DTO{
    private final int id;
    private final String responseMessage;

    public ResponseDTO(int id, String responseMessage) {
        this.id = id;
        this.responseMessage = responseMessage;
    }

    public int getId() {
        return id;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
