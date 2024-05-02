package com.alekseyruban.barbershopServer.helpers;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class RandomGenerator {

    static public String randomString(int length) {
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(symbols.length());
            sb.append(symbols.charAt(index));
        }

        return sb.toString();
    }

    static public String generateClientToken() {
        LocalDateTime today = LocalDateTime.now();

        String tokenValue = "" + (today.getYear() % 1000)
                + today.getDayOfYear() + RandomGenerator.randomString(10)
                + today.getHour() + today.getMinute();

        return tokenValue;
    }

    static public String randomPassword(int length) {
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(symbols.length());
            sb.append(symbols.charAt(index));
        }

        return sb.toString();
    }
}
