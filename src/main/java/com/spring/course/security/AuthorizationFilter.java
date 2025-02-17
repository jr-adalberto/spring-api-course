package com.spring.course.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.course.constant.SecurityConstants;
import com.spring.course.resource.exception.ApiError;
import com.spring.course.resource.exception.ApiErrorList;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer "
            try {
                Claims claims = jwtManager.parseToken(token);
                System.out.println("Token validated successfully: " + claims);
                filterChain.doFilter(request, response);
            } catch (JwtException e) {
                System.out.println("Token validation failed: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT token");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}