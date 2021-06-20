package com.kaonstudio.security.jwt.token;

import com.kaonstudio.security.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailVerificationTokenService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public void saveConfirmationToken(EmailVerificationToken token) {
        emailVerificationTokenRepository.save(token);
    }

    public Optional<EmailVerificationToken> getToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return emailVerificationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public int updateExpiresAt(String token) {
        return emailVerificationTokenRepository.updateExpiresAt(
                token, LocalDateTime.now().plusDays(14)
        );
    }

    public EmailVerificationToken createEmailVerificationToken(String jwtToken, AppUser appUser) {
        return new EmailVerificationToken(
                jwtToken,
                appUser,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(14)
        );
    }

}
