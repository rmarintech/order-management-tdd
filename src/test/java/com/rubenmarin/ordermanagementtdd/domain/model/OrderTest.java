package com.rubenmarin.ordermanagementtdd.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// UserStory1:
// Como cliente, quiero crear un pedido vacío para poder añadir productos posteriormente.
//      Un pedido recién creado debe cumplir estas condiciones:
//          1. Tener el estado DRAFT.
//          2. Tener un identificador único.
//          3. No contener productos.
//          4. Tener un importe total de cero.

class OrderTest {
     @Test
     void shouldCreateOrderWithDraftStatus() {
         // Act
         Order order = Order.create();
         // Assert
         Assertions.assertEquals(OrderStatus.DRAFT, order.getStatus());
     }
    @Test
    void shouldCreateOrderWithUniqueIdentifier() {
        // Act
        Order firstOrder = Order.create();
        Order secondOrder = Order.create();

        // Assert
        Assertions.assertNotNull(firstOrder.getOrderId());
        Assertions.assertNotNull(secondOrder.getOrderId());
        Assertions.assertNotEquals(firstOrder.getOrderId(), secondOrder.getOrderId());
    }

    @Test
    void shouldCreateOrderWithoutProducts() {
        // Act
        Order order = Order.create();
        // Assert
        Assertions.assertTrue(order.getOrderItems().isEmpty());
    }
}
