package com.codewithmosh.store.auth;

import com.codewithmosh.store.common.ErrorDto;
import com.codewithmosh.store.users.LoginRequest;

import com.codewithmosh.store.users.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtConfig jwtConfig;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        var tokens = authService.login(request.getEmail(), request.getPassword());

        var cookie = new Cookie("refreshToken", tokens.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(tokens.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        //System.out.println("AT REFRESH ENDPOINT");
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        return ResponseEntity.ok(authService.getMe());
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String token,
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        authService.revokeUserTokens(new AuthTokens(token, refreshToken));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorDto("Invalid user or password")
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleBadRefreshTokenException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorDto(ex.getMessage())
        );
    }
}
