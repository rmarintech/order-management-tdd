package com.rubenmarin.ordermanagementtdd.domain.model;

import java.util.UUID;

public record OrderId(UUID orderId) {

    public OrderId {
        if (orderId == null) {
            throw new IllegalStateException("orderId cannot be null");
        }
    }

    public static OrderId generateOrderId() {
        return new OrderId(UUID.randomUUID());
    }
}
