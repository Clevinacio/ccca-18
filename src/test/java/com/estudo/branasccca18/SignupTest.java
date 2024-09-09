package com.estudo.branasccca18;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SignupTest {

    @Test
    void createNewAccount_validData_returnsAccountId() {
        Map<String, Object> accountData = Map.of(
                "name", "John Doe",
                "email", "john.doe@example.com",
                "cpf", "12345678901",
                "carPlate", "ABC1234",
                "isPassenger", true,
                "isDriver", false,
                "password", "password123"
        );
        Signup signup = new Signup();
        UUID accountId = signup.createNewAccount(accountData);
        assertNotNull(accountId);
    }


}
