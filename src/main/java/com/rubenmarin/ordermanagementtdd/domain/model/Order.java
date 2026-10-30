package com.rubenmarin.ordermanagementtdd.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private OrderStatus status;
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
                .reduce(BigDecimal.ZERO,(a,b) -> (a.add(b)));
    }

    public void addOrderItem(ProductId productId, BigDecimal unitPrice, int quantity) {
        if(status!=OrderStatus.DRAFT){
            throw new IllegalStateException("Items can only be added to draft orders.");
        }
        OrderItem orderItem = new OrderItem(productId, unitPrice,  quantity) ;
        items.add(orderItem);
    }

    public void confirm() {
        if(status!=OrderStatus.DRAFT){
            throw new IllegalStateException("Only draft orders can be confirmed.");
        }
        if(items.isEmpty()) {
            throw new IllegalStateException("Order has no order items.");
        }
        status = OrderStatus.CONFIRMED;

    }

    public void cancel() {
        status = OrderStatus.CANCELLED;
    }
}
