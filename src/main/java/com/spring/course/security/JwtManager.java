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

    public String createToken(String email, List<String> roles, String secretKey, int days, String jwtProvider) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(calendar.getTime())
                .claim(SecurityConstants.JWT_ROLE_KEY, roles)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    public Claims parseToken(String jwt) throws JwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.API_KEY.getBytes())
                .parseClaimsJws(jwt)
                .getBody();


        return claims;
    }
}