package com.estudo.branasccca18;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateCpfTest {

    @Test
    @DisplayName("Deve validar um cpf com os digitos diferentes de zero")
    void test_valid_cpf_non_zero() {
        String cpf = "97456321558";
        boolean result = ValidateCpf.isValid(cpf);
        assertTrue(result);
    }

    @Test
    @DisplayName("Deve validar um cpf com o primeiro digito zero")
    void test_valid_cpf_first_digit_zero() {
        String cpf = "87748248800";
        boolean result = ValidateCpf.isValid(cpf);
        assertTrue(result);
    }

    @Test
    @DisplayName("Deve validar um cpf com o segundo digito zero")
    void test_valid_cpf_second_digit_zero() {
        String cpf = "71428793860";
        boolean result = ValidateCpf.isValid(cpf);
        assertTrue(result);
    }

    @Test
    @DisplayName("Não deve validar um cpf com menos de 11 caracteres")
    void test_invalid_cpf_less_than_11_characters() {
        String cpf = "1234567890";
        boolean result = ValidateCpf.isValid(cpf);
        assertFalse(result);
    }

    @Test
    @DisplayName("Não deve validar um cpf com todos os caracteres iguais")
    void test_invalid_cpf_all_characters_equal() {
        String cpf = "11111111111";
        boolean result = ValidateCpf.isValid(cpf);
        assertFalse(result);
    }

    @Test
    @DisplayName("Não deve validar um cpf com letras")
    void test_invalid_cpf_with_letters() {
        String cpf = "1234567890a";
        boolean result = ValidateCpf.isValid(cpf);
        assertFalse(result);
    }

}
