package com.apaza.authservice.repository;

import com.apaza.authservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser , Long> {

    Optional<AuthUser> findByUserName (String username);

}
