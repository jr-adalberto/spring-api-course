package com.spring.course.security;

import com.spring.course.service.util.HashUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return HashUtil.getSecureHash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = HashUtil.getSecureHash(rawPassword.toString());
        return hashedPassword.equals(encodedPassword);
    }
}