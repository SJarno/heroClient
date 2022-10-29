package com.sjarno.backend.api.rest.client.exceptions;

public class CustomException extends RuntimeException {

    private String test;
    public CustomException() {
        this.test = "";
    }

    public CustomException(String message) {
        super(message);
    }
    public void setTest(String test) {
        this.test = test;
    }
    public String getTest() {
        return test;
    }

}
