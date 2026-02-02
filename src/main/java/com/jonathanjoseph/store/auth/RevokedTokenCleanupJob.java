package com.jonathanjoseph.store.auth;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableScheduling
@AllArgsConstructor
@Component
public class RevokedTokenCleanupJob {
    private final RevokedTokenRepository revokedTokenRepository;

    @Transactional
    @Scheduled(fixedRateString = "#{${expiredTokenRemovalRate} * 1000}")
    public void removeExpiredTokens() {
        revokedTokenRepository.deleteByExpirationDateBefore(new Date());
    }
}
