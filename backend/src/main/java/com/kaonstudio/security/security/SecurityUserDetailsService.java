package com.kaonstudio.security.security;

import com.kaonstudio.security.appuser.AppUser;
import com.kaonstudio.security.appuser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    public static final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserRepository userRepository;

    @Autowired
    public SecurityUserDetailsService(
            @Qualifier("postgres") AppUserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        }
        return new SecurityUserDetails(user);
    }
}
