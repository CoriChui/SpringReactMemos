package com.kaonstudio.security.auth;

import com.kaonstudio.security.appuser.AppUser;
import com.kaonstudio.security.appuser.AppUserRepository;
import com.kaonstudio.security.auth.login.LoginRequest;
import com.kaonstudio.security.email.EmailService;
import com.kaonstudio.security.exception.ApiRequestException;
import com.kaonstudio.security.exception.email.EmailExpiredException;
import com.kaonstudio.security.jwt.JwtService;
import com.kaonstudio.security.jwt.token.EmailVerificationToken;
import com.kaonstudio.security.jwt.token.EmailVerificationTokenService;
import com.kaonstudio.security.auth.registration.RegistrationRequest;
import com.kaonstudio.security.security.SecurityUserDetails;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.kaonstudio.security.security.SecurityUserRole.USER;

@Service
@AllArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final EmailVerificationTokenService verificationTokenService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AppUser register(RegistrationRequest request) throws Exception {
        if (emailExists(request.getEmail())) {
            String message = "User with email %s already exists";
            throw new RuntimeException(String.format(message, request.getEmail()));
        }
        String encoded = passwordEncoder.encode(request.getPassword());
        AppUser user = new AppUser(
                request.getEmail(),
                encoded,
                request.getFirstName(),
                request.getLastName(),
                USER
        );
        String jwtToken = jwtService.generateEmailConfirmationToken(request.getEmail());
        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + jwtToken;
        emailService.send(request.getEmail(), emailService.getEmailText(request.getFirstName(), link));

        EmailVerificationToken verificationToken = verificationTokenService.createEmailVerificationToken(jwtToken, user);
        verificationTokenService.saveConfirmationToken(verificationToken);

        return user;
    }

    public String login(LoginRequest request) {
        Authentication usernamePasswordToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        try {
                 Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityUserDetails securityUser = (SecurityUserDetails) authentication.getPrincipal();

            return jwtService.generateAuthJwtToken(authentication.getName(), securityUser.getId().toString());
        } catch (AuthenticationException e) {
            throw new ApiRequestException("Invalid credentials");
        }
    }

    private boolean emailExists(String email) {
        return appUserRepository.findByEmail(email) != null;
    }

    @Transactional
    public void confirmToken(String token) throws
            RuntimeException {

        EmailVerificationToken verificationToken = verificationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Email verification token not found")
                );

        if (verificationToken.getConfirmedAt() != null) {
            throw new RuntimeException("Email already confirmed");
        }

        LocalDateTime expiresAt = verificationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new EmailExpiredException("Confirmation period expired", token);
        }

        verificationTokenService.setConfirmedAt(token);

        AppUser user = verificationToken.getAppUser();
        user.setEnabled(true);
        appUserRepository.save(user);
    }

    public void resendEmailVerificationToken(String token) throws Exception {
        EmailVerificationToken verificationToken = verificationTokenService
                .getToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        AppUser user = verificationToken.getAppUser();
        String jwtToken = verificationToken.getToken();

        verificationTokenService.updateExpiresAt(jwtToken);
        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + jwtToken;
        emailService.send(user.getEmail(), emailService.getEmailText(user.getFirstName(), link));
    }
}
