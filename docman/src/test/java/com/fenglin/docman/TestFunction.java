package com.fenglin.docman;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestFunction {
    public static void main(String[] args) {
        String password = "{bcrypt}$2a$10$p0l7CTkZSxqwWHalSOckE.BrxtYHwbY2.N.jKAs6gvBbbJdCf8i5q";

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        System.out.println(passwordEncoder.matches("123456",password));


    }
}
