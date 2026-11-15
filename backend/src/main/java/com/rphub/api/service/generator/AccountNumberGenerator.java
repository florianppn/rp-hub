package com.rphub.api.service.generator;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate(String codeBank) {
        return String.format("%s-%06d-%06d",
                codeBank,
                RANDOM.nextInt(1_000_000),
                RANDOM.nextInt(1_000_000)
        );
    }

}
