package com.spring.course.security;

import com.spring.course.service.util.HashUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String hash = HashUtil.getSecureHash(rawPassword.toString());
        return hash;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hash = HashUtil.getSecureHash(rawPassword.toString());
        return hash.equals(encodedPassword);
    }

}