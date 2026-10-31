package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @Column(nullable = false)
    UUID orderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(UUID orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public UUID getOrderId() {
        return orderId;
    }

}
