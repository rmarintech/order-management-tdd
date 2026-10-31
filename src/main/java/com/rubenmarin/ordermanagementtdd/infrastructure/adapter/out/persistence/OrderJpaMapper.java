package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderId;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderJpaMapper {

    public OrderJpaEntity toEntity(Order domain) {
        UUID orderId = domain.getOrderId().orderId();
        OrderStatus status = domain.getStatus();

        return new OrderJpaEntity(orderId, status);
    }

    public Order toDomain(OrderJpaEntity entity) {
        OrderId orderId = new OrderId(entity.orderId);
        OrderStatus status =   entity.status;

        return Order.restore(orderId, status);
    }
}

