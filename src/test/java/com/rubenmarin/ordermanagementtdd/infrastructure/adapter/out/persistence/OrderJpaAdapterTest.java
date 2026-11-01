package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.application.port.out.persistence.OrderRepository;
import com.rubenmarin.ordermanagementtdd.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
class OrderJpaAdapterTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldSaveAndRetrieveOrder() {

        Order order = Order.create();
        OrderId orderId = order.getOrderId();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 3;

        order.addOrderItem(productId, unitPrice, quantity);

        order.confirm();
        orderRepository.save(order);

        Optional<Order> foundOptional = orderRepository.findById(orderId);
        Assertions.assertTrue(foundOptional.isPresent());

        Order found = foundOptional.orElseThrow();
        Assertions.assertEquals(orderId, found.getOrderId());

        Assertions.assertEquals(OrderStatus.CONFIRMED, found.getStatus());
        Assertions.assertEquals(1, found.getOrderItems().size());

        OrderItem orderItem = found.getOrderItems().getFirst();
        Assertions.assertEquals(productId, orderItem.getProductId());
        Assertions.assertEquals(0, unitPrice.compareTo(orderItem.getUnitPrice()));
        Assertions.assertEquals(quantity, orderItem.getQuantity());


    }
}
