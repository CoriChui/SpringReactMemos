package com.kaonstudio.security.jwt.token;

import com.kaonstudio.security.appuser.AppUser;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "email_verification_token")
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;


    public EmailVerificationToken(String token,
                                  AppUser appUser,
                                  LocalDateTime createdAt,
                                  LocalDateTime expireAt
                                  ) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expireAt;
        this.appUser = appUser;
    }
}
