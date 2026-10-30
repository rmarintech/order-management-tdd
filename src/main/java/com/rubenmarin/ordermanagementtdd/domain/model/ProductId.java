package com.rubenmarin.ordermanagementtdd.domain.model;

import java.util.UUID;

public record ProductId(UUID productId) {

    public ProductId {
        if (productId == null) {
            throw new IllegalStateException("productId cannot be null");
        }
    }

    public static ProductId generateProductId() {
        return new ProductId(UUID.randomUUID());
    }
}
