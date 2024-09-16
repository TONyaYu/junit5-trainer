package com.dmdev.mapper;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubscriptionMapperTest {

    private final CreateSubscriptionMapper mapper = CreateSubscriptionMapper.getInstance();

    @Test
    void map() {

        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE.name())
                .expirationDate(Instant.now())
                .build();

        Subscription actualResult = mapper.map(dto);

        Subscription expectedResult = Subscription.builder()
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE)
                .expirationDate(Instant.now())
                .status(Status.ACTIVE)
                .build();
    }
}