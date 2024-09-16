package com.dmdev.dao;

import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.integration.IntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDaoIT extends IntegrationTestBase {

    private final SubscriptionDao subscriptionDao = SubscriptionDao.getInstance();

    @Test
    void findAll() {
        Subscription sub1 = subscriptionDao.insert(getSubscription());
        Subscription sub2 = subscriptionDao.insert(getSubscription());
        Subscription sub3 = subscriptionDao.insert(getSubscription());

        List<Subscription> actualResult = subscriptionDao.findAll();

        assertThat(actualResult).hasSize(3);
        List<Integer> userIds = actualResult.stream()
                .map(Subscription::getId)
                .toList();
        assertThat(userIds).contains(sub1.getId(), sub2.getId(), sub3.getId());

    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }

    @Test
    void findByUserId() {
    }

    private Subscription getSubscription() {
        return Subscription.builder()
                .userId(37)
                .name("Antony")
                .provider(Provider.APPLE)
                .expirationDate(Instant.now())
                .status(Status.ACTIVE)
                .build();
    };
}