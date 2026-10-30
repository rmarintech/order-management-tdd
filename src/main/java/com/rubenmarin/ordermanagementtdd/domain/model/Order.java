package com.rubenmarin.ordermanagementtdd.domain.model;

public class Order {

    private final OrderStatus status;
    private final OrderId orderId;

    private Order(){
        this.orderId = OrderId.generateOrderId();
        this.status = OrderStatus.DRAFT;
    }

    public static Order create(){
        return new Order();
    }

    public OrderStatus getStatus(){
        return this.status;
    }
    public OrderId getOrderId(){
        return this.orderId;
    }
}
