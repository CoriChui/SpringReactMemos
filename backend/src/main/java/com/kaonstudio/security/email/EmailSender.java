package com.kaonstudio.security.email;

public interface EmailSender {
    void send(String to, String email) throws Exception;
}
