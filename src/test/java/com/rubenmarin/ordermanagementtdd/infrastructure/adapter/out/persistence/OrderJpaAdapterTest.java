package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.application.port.out.persistence.OrderRepository;
import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class OrderJpaAdapterTest {

     @Autowired
     private OrderRepository orderRepository;

     @Test
     void shouldSaveAndRetrieveOrder(){

          Order order = Order.create();
          OrderId orderId = order.getOrderId();

          orderRepository.save(order);

          Optional<Order> found = orderRepository.findById(orderId);

          Assertions.assertTrue(found.isPresent());
          Assertions.assertEquals(orderId, found.get().getOrderId());
     }
}
