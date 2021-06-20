package com.kaonstudio.security.appuser;


import com.kaonstudio.security.jwt.token.EmailVerificationToken;
import com.kaonstudio.security.security.SecurityUserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled = false;
    @Enumerated
    private SecurityUserRole role;

    public AppUser(String email, String password, String firstName, String lastName, SecurityUserRole role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
