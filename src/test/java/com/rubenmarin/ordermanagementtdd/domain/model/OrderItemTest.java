package com.rubenmarin.ordermanagementtdd.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


/**
 *
 * User Story 2:
 * Como cliente, quiero añadir productos a mi pedido indicando
 * su cantidad y precio unitario para calcular el importe total.
 * 1. Añadir un OrderItem a un pedido en estado DRAFT.
 * 2. Comprobar que la cantidad es positiva.
 * 3. Comprobar que el precio unitario no es negativo.
 * 4. Calcular el importe total de una línea.
 * 5. Calcular el importe total del pedido.
 * 6. Impedir modificaciones cuando el pedido no esté en
 * estado DRAFT (cuando incorporemos otros estados).
 */

class OrderItemTest {

    @Test
    void shouldCalculateOrderItemTotalPrice() {
        // Arrange
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 10;

        OrderItem orderItem = new  OrderItem( productId,  unitPrice,  quantity);
        // Act
        BigDecimal totalPrice = orderItem.calculateTotalPrice();

        // Assert
        Assertions.assertEquals(new BigDecimal("100"), totalPrice);

    }

}
