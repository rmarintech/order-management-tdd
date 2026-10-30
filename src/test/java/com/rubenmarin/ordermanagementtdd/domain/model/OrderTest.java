package com.rubenmarin.ordermanagementtdd.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// UserStory1:
// Como cliente, quiero crear un pedido vacío para poder añadir productos posteriormente.
//      Un pedido recién creado debe cumplir estas condiciones:
//          1. Tener un identificador único.
//          2. Tener el estado DRAFT.
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
}
