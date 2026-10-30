package com.rubenmarin.ordermanagementtdd.application.service;

import com.rubenmarin.ordermanagementtdd.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GetOrderServiceTest {

    /**
     * User Story 5:
     * Como cliente, quiero consultar un pedido
     * mediante su identificador para conocer su estado, productos e importe total.
     */

    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    GetOrderService getOrderService;

    @Test
    void test_GetOrderService() {
        Order order = Order.create();
        OrderId orderId = order.getOrderId();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TWO;
        int quantity = 10;
        order.addOrderItem(productId, unitPrice, quantity);


        Mockito.when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));

        Order found = getOrderService.findById(orderId).orElseThrow();
        OrderItem item = found.getOrderItems().stream().findFirst().orElseThrow();


        Assertions.assertAll(
                () -> Assertions.assertEquals(order.getOrderId(), found.getOrderId()),
                () -> Assertions.assertEquals(OrderStatus.DRAFT, found.getStatus()),

                () -> Assertions.assertEquals(item.getProductId(), productId),
                () -> Assertions.assertEquals(item.getUnitPrice(), unitPrice),
                () -> Assertions.assertEquals(item.getQuantity(), quantity)

        );

    }
}
