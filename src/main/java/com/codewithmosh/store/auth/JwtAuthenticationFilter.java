package com.codewithmosh.store.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //System.out.println("refresh filtering");
        var authHeader = request.getHeader("Authorization");
        System.out.println("Auth header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Good token format");

        var token = authHeader.replace("Bearer ", "");
        var jwt = jwtService.parse(token);

        //System.out.println("Auth Header: " + request.getHeader("Authorization"));

        if (jwt == null || jwt.isExpired()) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Valid token");
        System.out.println("Token ID: " + jwt.getIdFromToken());

        var authentication = new UsernamePasswordAuthenticationToken(
                jwt.getIdFromToken(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + jwt.getRoleFromToken()))
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //System.out.println("Next filter");
        System.out.println(request.getRequestURI());
        filterChain.doFilter(request, response);
        System.out.println(response.getStatus());
    }
}
