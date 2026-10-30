package com.rubenmarin.ordermanagementtdd.domain.model;

public class Order {

    private final OrderStatus status;

    private Order(){
        this.status = OrderStatus.DRAFT;
    }

    public static Order create(){
        return new Order();
    }

    public OrderStatus getStatus(){
        return this.status;
    }
}
