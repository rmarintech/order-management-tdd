package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    List<OrderItemJpaEntity> items = new ArrayList<>();

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(UUID orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public void addOrderItem(OrderItemJpaEntity item) {
        items.add(item);
        item.setOrder(this);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItemJpaEntity> getItems() {
        return items;
    }
}
