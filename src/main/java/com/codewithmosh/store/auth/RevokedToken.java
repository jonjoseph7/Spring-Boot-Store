package com.codewithmosh.store.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "revoked_tokens")
public class RevokedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "token_hash", length = 64)
    private String tokenHash;

    @NotNull
    @Column(name = "expiration_date")
    private Date expirationDate;

    public RevokedToken(String tokenHash, Date expirationDate) {
        this.tokenHash = tokenHash;
        this.expirationDate = expirationDate;
    }
}