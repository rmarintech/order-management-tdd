package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderJpaMapper {

    public OrderJpaEntity toOrderEntity(Order order) {
        UUID orderId = order.getOrderId().orderId();
        OrderStatus status = order.getStatus();

        OrderJpaEntity orderEntity = new OrderJpaEntity(orderId, status);

        order.getOrderItems().forEach(orderItem -> {
            orderEntity.addOrderItem(toOrderItemEntity(orderItem));
        });

        return orderEntity;
    }

    public Order toOrderDomain(OrderJpaEntity orderEntity) {
        OrderId orderId = new OrderId(orderEntity.orderId);
        OrderStatus status = orderEntity.status;
        List<OrderItem> items = new ArrayList<OrderItem>();

        orderEntity.getItems().forEach(itemEntity -> {
            items.add(toOrderItemDomain(itemEntity));
        });

        return Order.restore(orderId, status, items);
    }

    public OrderItemJpaEntity toOrderItemEntity(OrderItem item) {

        UUID productId = item.getProductId().productId();
        Integer quantity = item.getQuantity();
        BigDecimal unitPrice = item.getUnitPrice();

        return new OrderItemJpaEntity(productId, quantity, unitPrice);
    }

    public OrderItem toOrderItemDomain(OrderItemJpaEntity itemEntity) {


        ProductId productId = new ProductId(itemEntity.productId);
        BigDecimal unitPrice = itemEntity.unitPrice;
        int quantity = itemEntity.quantity;


        return new OrderItem(productId, unitPrice, quantity);
    }
}

