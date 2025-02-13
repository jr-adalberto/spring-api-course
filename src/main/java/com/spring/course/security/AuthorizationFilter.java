package com.spring.course.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.course.constant.SecurityConstants;
import com.spring.course.resource.exception.ApiError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final ObjectMapper objectMapper;

    public AuthorizationFilter(JwtManager jwtManager, ObjectMapper objectMapper) {
        this.jwtManager = jwtManager;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null) {
            try {

                Claims claims = jwtManager.parseToken(token, SecurityConstants.API_KEY);

                List<String> roles = (List<String>) claims.get(SecurityConstants.JWT_ROLE_KEY);

                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                Authentication auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (JwtException e) {

                ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), "Invalid token", new Date());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(new ObjectMapper().writeValueAsString(apiError));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ApiError apiError = new ApiError(status.value(), message, new Date());
        String apiErrorString = objectMapper.writeValueAsString(apiError);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        try (PrintWriter writer = response.getWriter()) {
            writer.write(apiErrorString);
        }
    }
}