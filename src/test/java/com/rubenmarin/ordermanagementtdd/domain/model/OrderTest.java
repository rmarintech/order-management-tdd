package com.rubenmarin.ordermanagementtdd.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

/**
 *
 * User Story 1:
 * Como cliente, quiero crear un pedido vacío para poder añadir productos posteriormente.
 * Un pedido recién creado debe cumplir estas condiciones:
 * 1. Tener el estado DRAFT.
 * 2. Tener un identificador único.
 * 3. No contener productos.
 * 4. Tener un importe total de cero.
 */

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

    @Test
    void shouldCreateOrderWithTotalPriceEqualsToZero() {
        // Act
        Order order = Order.create();
        // Assert
        Assertions.assertEquals(BigDecimal.ZERO, order.getTotalPrice());

    }

    @Test
    void shouldAddOrderItemToDraftOrder() {
        // Arange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 10;

        // Act
        order.addOrderItem(productId, unitPrice, quantity);

        // Assert
        Assertions.assertEquals(1, order.getOrderItems().size());

    }

    @ParameterizedTest
    @ValueSource(ints = {-1,0})
    void shouldRejectNegativeOrZeroQuantity(int quantity) {
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }

    @Test
    void shouldRejectNegativeUnitPrice(){
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = new  BigDecimal("-1");
        int quantity = 1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });

    }

    @Test
    void shouldAllowZeroUnitPrice() {
        // Arrange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.ZERO;
        int quantity = 10;

        // Act
        order.addOrderItem(productId, unitPrice, quantity);

        // Assert
        Assertions.assertEquals(BigDecimal.ZERO, order.getOrderItems().getFirst().getUnitPrice());

    }

    @Test
    void shouldRejectNullUnitPrice(){
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();;
        BigDecimal unitPrice = null;
        int quantity = 1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }

    @Test
    void shouldRejectNullProductId(){
        Order order = Order.create();
        ProductId productId = null;
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }


/**
            * 4. Calcular el importe total de una línea.
            * 5. Calcular el importe total del pedido.
            * 6. Impedir modificaciones cuando el pedido no esté en
            * estado DRAFT (cuando incorporemos otros estados).
     * */
}
