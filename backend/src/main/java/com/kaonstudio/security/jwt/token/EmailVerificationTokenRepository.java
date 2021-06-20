package com.kaonstudio.security.jwt.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "UPDATE email_verification_token SET confirmed_at = ?2 WHERE token LIKE ?1", nativeQuery = true)
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query(value = "UPDATE email_verification_token SET expires_at = ?2 WHERE token LIKE ?1", nativeQuery = true)
    int updateExpiresAt(String token,
                          LocalDateTime expiresAt);

}


