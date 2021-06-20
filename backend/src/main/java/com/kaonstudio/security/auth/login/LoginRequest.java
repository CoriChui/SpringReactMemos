package com.kaonstudio.security.auth.login;

import lombok.*;

@Data
public class LoginRequest {
    private final String email;
    private final String password;
}
