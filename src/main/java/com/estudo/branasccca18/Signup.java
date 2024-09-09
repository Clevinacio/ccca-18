package com.estudo.branasccca18;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController("/signup")
public class Signup {

    private static final int INVALID_CPF = -1;
    private static final int INVALID_EMAIL = -2;
    private static final int INVALID_NAME = -3;
    private static final int ACCOUNT_ALREADY_EXISTS = -4;
    private static final int INVALID_CAR_PLATE = -5;
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource("jdbc:postgresql://localhost:5432/app", "postgres", "123456"));

    @PostMapping
    public ResponseEntity<Map<String, Object>> signup(@RequestBody Map<String, Object> accountData) {
        try {
            int errorCode = validateAccountData(accountData);
            if (errorCode != 0) return sendErrorMessage(errorCode);
            UUID accountDataId = createNewAccount(accountData);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("accountId", accountDataId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", Integer.valueOf(e.getMessage())));
        }
    }

    private int validateAccountData(Map<String, Object> accountData) {
        String queryFindAccountByEmail = "SELECT COUNT(*) FROM ccca.account WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(queryFindAccountByEmail, Integer.class, accountData.get("email"));
        if (count != null && count > 0) return ACCOUNT_ALREADY_EXISTS;
        if (isNameInvalid(accountData.get("name").toString())) return INVALID_NAME;
        if (isEmailInvalid(accountData.get("email").toString())) return INVALID_EMAIL;
        if (isCpfInvalid(accountData.get("cpf").toString())) return INVALID_CPF;
        if (isDriver(accountData.get("isDriver").toString()) && carPlateInvalid(accountData.get("carPlate").toString()))
            return INVALID_CAR_PLATE;
        return 0;
    }

    private ResponseEntity<Map<String, Object>> sendErrorMessage(int errorCode) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("message", errorCode));
    }

    UUID createNewAccount(Map<String, Object> accountData) {
        UUID accountDataId = UUID.randomUUID();
        String queryInsertNewAccount = "INSERT INTO ccca.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver, password) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(queryInsertNewAccount, accountDataId, accountData.get("name"), accountData.get("email"), accountData.get("cpf"), accountData.get("carPlate"),
                accountData.get("isPassenger"), accountData.get("isDriver"), accountData.get("password"));
        return accountDataId;
    }

    private boolean isNameInvalid(String name) {
        return !Pattern.matches("[a-zA-Z] [a-zA-Z]+", name);
    }

    private boolean isEmailInvalid(String email) {
        return !Pattern.matches("^(.+)@(.+)$", email);
    }

    private boolean isCpfInvalid(String cpf) {
        return !ValidateCpf.isValid(cpf);
    }

    private boolean isDriver(String isDriver) {
        return isDriver.equals("true");
    }

    private boolean carPlateInvalid(String carPlate) {
        return !Pattern.matches("[A-Z]{3}[0-9]{4}", carPlate);
    }
}
