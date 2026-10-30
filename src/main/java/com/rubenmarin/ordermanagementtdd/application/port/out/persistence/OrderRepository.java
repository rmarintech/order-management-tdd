package com.rubenmarin.ordermanagementtdd.application.port.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderId;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(OrderId orderId);
}
