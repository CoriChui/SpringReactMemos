package com.kaonstudio.security.appuser;

import com.kaonstudio.security.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.OptionalInt;

@Repository("postgres")
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE app_user SET enabled = TRUE WHERE email LIKE ?1", nativeQuery = true)
    int enableAppUser(String email);

}
