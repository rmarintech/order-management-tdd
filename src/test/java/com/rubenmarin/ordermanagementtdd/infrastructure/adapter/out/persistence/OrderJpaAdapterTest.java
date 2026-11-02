package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.application.port.out.persistence.OrderRepository;
import com.rubenmarin.ordermanagementtdd.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class OrderJpaAdapterTest {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * User Story 6:
     * Como cliente, quiero que mis pedidos se almacenen de forma persistente
     * para poder recuperarlos posteriormente, conservando su estado,
     * productos e importes.
     */

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


    @Test
    void shouldSaveAndRetrieveOrderWithMultipleItems() {

        Order order = Order.create();
        OrderId orderId = order.getOrderId();
        ProductId productId1 = ProductId.generateProductId();
        ProductId productId2 = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 3;

        order.addOrderItem(productId1, unitPrice, quantity);
        order.addOrderItem(productId2, unitPrice, quantity);

        orderRepository.save(order);

        Optional<Order> foundOptional = orderRepository.findById(orderId);
        Assertions.assertTrue(foundOptional.isPresent());

        Order found = foundOptional.orElseThrow();
        Assertions.assertEquals(orderId, found.getOrderId());

        Assertions.assertEquals(OrderStatus.DRAFT, found.getStatus());
        Assertions.assertEquals(2, found.getOrderItems().size());

        OrderItem orderItem1 = found.getOrderItems().stream().filter(p -> p.getProductId().equals(productId1)).findFirst().orElseThrow();
        Assertions.assertEquals(productId1, orderItem1.getProductId());
        Assertions.assertEquals(0, unitPrice.compareTo(orderItem1.getUnitPrice()));
        Assertions.assertEquals(quantity, orderItem1.getQuantity());

        OrderItem orderItem2 = found.getOrderItems().stream().filter(p -> p.getProductId().equals(productId2)).findFirst().orElseThrow();
        Assertions.assertEquals(productId2, orderItem2.getProductId());
        Assertions.assertEquals(0, unitPrice.compareTo(orderItem2.getUnitPrice()));
        Assertions.assertEquals(quantity, orderItem2.getQuantity());
    }

    /**
     * Requisito: al guardar un pedido que ya existe en PostgreSQL, debemos actualizarlo sin generar un pedido duplicado.
     *
     */
    @Test
    void shouldUpdateExistingOrder() {

        Order order = Order.create();
        OrderId orderId = order.getOrderId();
        ProductId productId1 = ProductId.generateProductId();
        ProductId productId2 = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 3;

        order.addOrderItem(productId1, unitPrice, quantity);


        orderRepository.save(order);

        Optional<Order> foundOptional = orderRepository.findById(orderId);
        Assertions.assertTrue(foundOptional.isPresent());

        Order found = foundOptional.orElseThrow();
        Assertions.assertEquals(orderId, found.getOrderId());

        Assertions.assertEquals(OrderStatus.DRAFT, found.getStatus());
        Assertions.assertEquals(1, found.getOrderItems().size());

        OrderItem orderItem1 = found.getOrderItems().stream().filter(p -> p.getProductId().equals(productId1)).findFirst().orElseThrow();
        Assertions.assertEquals(productId1, orderItem1.getProductId());
        Assertions.assertEquals(0, unitPrice.compareTo(orderItem1.getUnitPrice()));
        Assertions.assertEquals(quantity, orderItem1.getQuantity());


        found.addOrderItem(productId2, unitPrice, quantity);

        orderRepository.save(found);

        Optional<Order> foundOptionalAgain = orderRepository.findById(orderId);
        Assertions.assertTrue(foundOptionalAgain.isPresent());

        Order foundAgain = foundOptionalAgain.orElseThrow();
        Assertions.assertEquals(2, foundAgain.getOrderItems().size());

        Assertions.assertEquals(orderId, foundAgain.getOrderId());

        OrderItem orderItem1Again = foundAgain.getOrderItems().stream().filter(p -> p.getProductId().equals(productId1)).findFirst().orElseThrow();
        Assertions.assertEquals(productId1, orderItem1Again.getProductId());
        Assertions.assertEquals(0, unitPrice.compareTo(orderItem1Again.getUnitPrice()));
        Assertions.assertEquals(quantity, orderItem1Again.getQuantity());

        OrderItem orderItem2 = foundAgain.getOrderItems().stream().filter(p -> p.getProductId().equals(productId2)).findFirst().orElseThrow();
        Assertions.assertEquals(productId2, orderItem2.getProductId());
        Assertions.assertEquals(0, unitPrice.compareTo(orderItem2.getUnitPrice()));
        Assertions.assertEquals(quantity, orderItem2.getQuantity());

    }

    @Test
    void shouldUpdateExistingOrderStatus(){

        Order order = Order.create();
        order.addOrderItem(ProductId.generateProductId(), BigDecimal.TEN, 3);
        orderRepository.save(order);

        Optional<Order> foundOptional = orderRepository.findById(order.getOrderId());
        Assertions.assertTrue(foundOptional.isPresent());

        Order found = foundOptional.orElseThrow();
        Assertions.assertEquals(order.getOrderId(), found.getOrderId());
        Assertions.assertEquals(OrderStatus.DRAFT, found.getStatus());
        Assertions.assertEquals(1, found.getOrderItems().size());

        found.confirm();
        orderRepository.save(found);

        Optional<Order> foundOptionalAgain = orderRepository.findById(order.getOrderId());
        Assertions.assertTrue(foundOptionalAgain.isPresent());

        Order foundAgain = foundOptionalAgain.orElseThrow();
        Assertions.assertEquals(order.getOrderId(), foundAgain.getOrderId());
        Assertions.assertEquals(OrderStatus.CONFIRMED, foundAgain.getStatus());
        Assertions.assertEquals(1, foundAgain.getOrderItems().size());
    }

    @Test
    void shouldReturnEmptyOptionalWhenOrderIsNotFound(){
        Optional<Order> foundOptional = orderRepository.findById(new OrderId(UUID.randomUUID()));
        Assertions.assertTrue(foundOptional.isEmpty());
    }


}
