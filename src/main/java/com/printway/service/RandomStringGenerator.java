package com.printway.service;

import java.security.SecureRandom;

public class RandomStringGenerator {
    public String generateRandomString() {
        int length = 8;
        String CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBER = "0123456789";
        String DATA_FOR_RANDOM_STRING = CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }
}
