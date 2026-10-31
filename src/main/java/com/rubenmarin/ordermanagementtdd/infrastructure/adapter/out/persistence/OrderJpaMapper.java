package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderId;
import org.springframework.stereotype.Component;

@Component
public class OrderJpaMapper {

    public  OrderJpaEntity toEntity(Order domain) {
        return new OrderJpaEntity(domain.getOrderId().orderId());
    }

    public  Order toDomain(OrderJpaEntity entity) {
        return Order.restore(new OrderId(entity.orderId));
    }
}

