package com.spring.course.security;

import com.spring.course.constant.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class JwtManager {

    public String createToken(String email, List<String> roles, String secretKey, long validityInMilliseconds, String jwtProvider) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, (int) validityInMilliseconds);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(calendar.getTime())
                .claim(SecurityConstants.JWT_ROLE_KEY, roles)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    public Claims parseToken(String jwt, String secretKey) throws JwtException {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
    }
}