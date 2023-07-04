package com.sopra.spring.jpa.h2.exception;

public class CustomEception extends RuntimeException {

    public CustomEception(String message) {
        super(message);
    }
}
