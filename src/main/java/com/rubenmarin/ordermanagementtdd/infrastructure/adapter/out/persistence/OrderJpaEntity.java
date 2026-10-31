package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @Column(nullable = false)
    UUID orderId;

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

}
