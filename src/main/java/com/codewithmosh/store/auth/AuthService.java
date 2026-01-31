package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.User;
import com.codewithmosh.store.users.UserDto;
import com.codewithmosh.store.users.UserMapper;
import com.codewithmosh.store.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        return userRepository.findById(userId).orElseThrow();
    }

    public AuthTokens login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        var user = userRepository.findByEmail(email).orElseThrow();
        var accessJwt = jwtService.generateAccessToken(user);
        var refreshJwt = jwtService.generateRefreshToken(user);
        return new AuthTokens(accessJwt, refreshJwt);
    }

    public JwtResponse refresh(String refreshToken) {
        var refreshJwt = jwtService.parse(refreshToken);
        if (refreshJwt == null || refreshJwt.isExpired()) {
            throw new AccessDeniedException("You are not currently logged in");
        }
        var user = userRepository.findById(refreshJwt.getIdFromToken()).orElseThrow();
        var accessJwt = jwtService.generateAccessToken(user);
        return new JwtResponse(accessJwt.toString());
    }

    public UserDto getMe() {
        return userMapper.toDto(getCurrentUser());
    }

    public void revokeUserTokens(AuthTokens tokens) {
        jwtService.revokeTokens(tokens.getAccessToken());
        jwtService.revokeTokens(tokens.getRefreshToken());
    }
}
