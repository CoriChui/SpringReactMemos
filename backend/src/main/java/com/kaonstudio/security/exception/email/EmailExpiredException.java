package com.kaonstudio.security.exception.email;

public class EmailExpiredException extends RuntimeException{

    private final String token;

    public String getToken() {
        return token;
    }

    public EmailExpiredException(String message, String token) {
        super(message);
        this.token = token;
    }
}
