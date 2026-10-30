package com.rubenmarin.ordermanagementtdd.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private final ProductId productId;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(ProductId productId, BigDecimal unitPrice, int quantity) {

        this.productId =Objects.requireNonNull(productId);
        this.unitPrice = Objects.requireNonNull(unitPrice);

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }

        this.quantity = quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
