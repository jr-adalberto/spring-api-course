package com.spring.course.service.util;


import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {

    public static String getSecureHash(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return DigestUtils.sha256Hex(input);
    }
}

