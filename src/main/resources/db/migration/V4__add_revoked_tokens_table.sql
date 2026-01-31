create table revoked_tokens
(
    id              bigint auto_increment
        primary key,
    token_hash      char(64)  not null,
    expiration_date timestamp not null,
    constraint revoked_tokens_token_hash
        unique (token_hash)
);
