package com.kaonstudio.security.auth.registration;

import com.kaonstudio.security.utils.PasswordMatch;
import com.kaonstudio.security.utils.ValidEmail;
import lombok.*;

import javax.validation.constraints.*;

@Data
@PasswordMatch
public class RegistrationRequest {

    @NotNull
    @NotBlank
    @ValidEmail
    private final String email;
    @NotNull
    @NotBlank
    private final String firstName;
    @NotNull
    @NotBlank
    private final String lastName;
    @NotNull
    @NotBlank
    private final String password;
    @NotNull
    @NotBlank
    private final String passwordMatch;

}
