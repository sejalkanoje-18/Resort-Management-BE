package com.ResortManagementBE.RRMS.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = "admin123";

        String encodedPassword = encoder.encode(password);

        System.out.println(encodedPassword);
    }
}
