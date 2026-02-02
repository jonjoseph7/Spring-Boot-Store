package com.jonathanjoseph.store.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    boolean existsByTokenHash(String tokenHash);
    void deleteByExpirationDateBefore(Date date);
}