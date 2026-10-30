package com.rubenmarin.ordermanagementtdd.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private final ProductId productId;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(ProductId productId, BigDecimal unitPrice, int quantity) {


        if (quantity <= 0) {
           throw new IllegalArgumentException("Quantity must be greater than zero.");
        }


        this.productId = productId;
        this.unitPrice = unitPrice;
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
