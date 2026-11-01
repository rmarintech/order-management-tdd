package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderItemId;
    @Column(nullable = false)
    UUID productId;
    @Column(nullable = false)
    Integer quantity;
    @Column(nullable = false)
    BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;


    public OrderItemJpaEntity() {
    }

    public OrderItemJpaEntity( UUID productId, Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

    }

    public void setOrder(OrderJpaEntity order) {
        this.order = order;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public OrderJpaEntity getOrder() {
        return order;
    }
}
