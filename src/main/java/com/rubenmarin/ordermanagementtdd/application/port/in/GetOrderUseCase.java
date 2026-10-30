package com.rubenmarin.ordermanagementtdd.application.port.in;

import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderId;

import java.util.Optional;

public interface GetOrderUseCase {

    Optional<Order> findById(OrderId orderId);
}
