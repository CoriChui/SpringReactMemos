package com.kaonstudio.security.utils;

import com.kaonstudio.security.auth.registration.RegistrationRequest;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        RegistrationRequest request = (RegistrationRequest) obj;
        if (request.getPassword() == null || request.getPasswordMatch() == null) return false;
        return request.getPassword().equals(request.getPasswordMatch());
    }
}
