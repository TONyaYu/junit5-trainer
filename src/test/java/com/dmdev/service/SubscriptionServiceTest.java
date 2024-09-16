package com.dmdev.service;

import com.dmdev.dao.SubscriptionDao;
import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.exception.ValidationException;
import com.dmdev.mapper.CreateSubscriptionMapper;
import com.dmdev.validator.CreateSubscriptionValidator;
import com.dmdev.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private CreateSubscriptionMapper createSubscriptionMapper;
    @Mock
    private CreateSubscriptionValidator createSubscriptionValidator;
    @Mock
    private Clock clock;
    @InjectMocks
    private SubscriptionService subscriptionService;


    @Test
    void upsertUpdateSubscription() {
        CreateSubscriptionDto dto = getDto();
        Subscription subscription = getSubscription();
        when(createSubscriptionValidator.validate(dto)).thenReturn(new ValidationResult());
        when(subscriptionDao.findByUserId(dto.getUserId())).thenReturn(List.of(subscription));
        when(subscriptionDao.upsert(any(Subscription.class))).thenReturn(subscription);
        Subscription actualResult = subscriptionService.upsert(dto);

        assertEquals(Status.ACTIVE, actualResult.getStatus());

        verify(subscriptionDao, times(1)).upsert(subscription);
    }
    @Test
    void upsertValidationFails() {
        CreateSubscriptionDto dto = getDto();
        Subscription subscription = getSubscription();
        ValidationResult validationResult = new ValidationResult();
        when(createSubscriptionValidator.validate(dto)).thenReturn(validationResult);

        assertThrows(ValidationException.class, () -> subscriptionService.upsert(dto));
    }

    @Test
    void upsertCreateNewSubscription() {
        CreateSubscriptionDto dto = getDto();
        Subscription subscription = getSubscription();
        when(createSubscriptionValidator.validate(dto)).thenReturn(new ValidationResult());
        when(subscriptionDao.findByUserId(dto.getUserId())).thenReturn(List.of());
        when(createSubscriptionMapper.map(dto)).thenReturn(subscription);
        when(subscriptionDao.upsert(any(Subscription.class))).thenReturn(subscription);

        // Act
        Subscription result = subscriptionService.upsert(dto);

        // Assert
        assertNotNull(result);
        assertEquals(Status.ACTIVE, result.getStatus());
        verify(subscriptionDao, times(1)).upsert(subscription);
    }

    @Test
    void cancelSuccess() {

    }

    @Test
    void cancelSubscriptionNotFound() {

    }

    @Test
    void cancelSubscriptionNotActive() {

    }

    @Test
    void expireSuccess() {
    }

    @Test
    void expireSubscriptionNotFound() {
    }

    @Test
    void expireSubscriptionAlreadyExpired() {
    }

    private CreateSubscriptionDto getDto() {
        return CreateSubscriptionDto.builder()
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now())
                .build();
    }

    private Subscription getSubscription() {
        return Subscription.builder()
                .id(13)
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE)
                .expirationDate(Instant.now())
                .status(Status.ACTIVE)
                .build();
    };
}