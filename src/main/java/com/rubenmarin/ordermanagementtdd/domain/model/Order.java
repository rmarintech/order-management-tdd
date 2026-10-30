package com.rubenmarin.ordermanagementtdd.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private final OrderStatus status;
    private final OrderId orderId;
    private final List<OrderItem> items;

    private Order() {
        this.orderId = OrderId.generateOrderId();
        this.status = OrderStatus.DRAFT;
        this.items = new ArrayList<>();
    }

    public static Order create() {
        return new Order();
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public OrderId getOrderId() {
        return this.orderId;
    }

    public List<OrderItem> getOrderItems() {
        // Evita order.getOrderItems().add(item);
        return Collections.unmodifiableList(items);
    }

    public BigDecimal getTotalPrice() {
        return items
                .stream()
                .map(orderItem -> orderItem.calculateTotalPrice())
                .reduce((a,b) -> (a.add(b)))
                .orElse(BigDecimal.ZERO);
    }

    public void addOrderItem(ProductId productId, BigDecimal unitPrice, int quantity) {

        OrderItem orderItem = new OrderItem(productId, unitPrice,  quantity) ;
        items.add(orderItem);
    }
}
