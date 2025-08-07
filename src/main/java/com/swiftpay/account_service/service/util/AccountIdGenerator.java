package com.swiftpay.account_service.service.util;

import javax.swing.text.DateFormatter;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountIdGenerator {

    private static final int R_LENGTH = 4;
    private static final String PREFIX = "ACCT";

    public static String generateAccountId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        String randomPart = generateRandomString(R_LENGTH);
        return PREFIX + datePart + randomPart;
    }

    private static String generateRandomString(int r_LENGTH) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(r_LENGTH);
        for(int i=0; i< r_LENGTH;i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }



}
