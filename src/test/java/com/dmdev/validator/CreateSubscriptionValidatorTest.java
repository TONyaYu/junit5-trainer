package com.dmdev.validator;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateSubscriptionValidatorTest {

    private final CreateSubscriptionValidator validator = CreateSubscriptionValidator.getInstance();

    @Test
    void shouldPassValidate() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(37)
                .name("Antony")
                .provider("TestProvider")
                .expirationDate(Instant.now())
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertTrue(actualValidationResult.hasErrors());
    }
    
    @Test
    void invalidUserId() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(null)
                .name("Antony")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now())
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertThat(actualValidationResult.getErrors()).hasSize(1);
        assertThat(actualValidationResult.getErrors().get(0).getCode()).isEqualTo(100);
    }

    @Test
    void invalidName() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(37)
                .name(null)
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now())
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertThat(actualValidationResult.getErrors()).hasSize(1);
        assertThat(actualValidationResult.getErrors().get(0).getCode()).isEqualTo(101);
    }

    @Test
    void invalidProvider() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(37)
                .name("Antony")
                .provider(null)
                .expirationDate(Instant.now())
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertThat(actualValidationResult.getErrors()).hasSize(1);
        assertThat(actualValidationResult.getErrors().get(0).getCode()).isEqualTo(102);
    }

    @Test
    void invalidExpirationDateNull() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE.name())
                .expirationDate(null)
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertThat(actualValidationResult.getErrors()).hasSize(1);
        assertThat(actualValidationResult.getErrors().get(0).getCode()).isEqualTo(103);
    }

    @Test
    void invalidExpirationDateIsBefore() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now().minus(1, ChronoUnit.DAYS))
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertThat(actualValidationResult.getErrors()).hasSize(1);
        assertThat(actualValidationResult.getErrors().get(0).getCode()).isEqualTo(103);
    }

    @Test
    void invalidUserIdNameProviderExpirationDate() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(null)
                .name(null)
                .provider(null)
                .expirationDate(null)
                .build();

        ValidationResult actualValidationResult = validator.validate(dto);

        assertThat(actualValidationResult.getErrors()).hasSize(4);
        List<Integer> errorCodes = actualValidationResult.getErrors().stream()
                        .map(Error::getCode)
                                .toList();
        assertThat(errorCodes).contains(100, 101, 102, 103);
    }

}