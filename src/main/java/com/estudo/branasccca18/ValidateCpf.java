package com.estudo.branasccca18;

public class ValidateCpf {
    private static final int CPF_VALID_LENGTH = 11;
    private static final int FIRST_DIGIT_FACTOR = 10;
    private static final int SECOND_DIGIT_FACTOR = 11;

    public static boolean isValid(String cpf) {
        cpf = cpf.replaceAll("/\\D/g", "");
        if (cpf.length() != CPF_VALID_LENGTH) return false;
        if(allDigitsTheSame(cpf)) return false;
        final int digit1 = calculateDigit(cpf, FIRST_DIGIT_FACTOR);
        final int digit2 = calculateDigit(cpf, SECOND_DIGIT_FACTOR);
        String lastTwoDigits = String.format("%d%d", digit1, digit2);
        return lastTwoDigits.equals(extractDigits(cpf));
    }

    private static boolean allDigitsTheSame(String cpf) {
        return cpf.chars().distinct().count() == 1;
    }

    private static int calculateDigit(String cpf, int firstDigitFactor) {
        int total = 0;
        for (int i = 0; i < cpf.length(); i++) {
            if (firstDigitFactor >1)
                total += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (firstDigitFactor--);
        }
        int rest = total % 11;
        return rest < 2 ? 0 : 11 - rest;
    }

    private static String extractDigits(String cpf) {
        return cpf.substring(cpf.length() - 2);
    }
}
