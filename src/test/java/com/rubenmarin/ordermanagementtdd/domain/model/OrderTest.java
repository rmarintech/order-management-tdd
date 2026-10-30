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

/**
 *
 * User Story 3 — Confirmar pedidos
 * 1. Un pedido DRAFT con productos puede pasar a CONFIRMED.
 * 2. Un pedido vacío no puede confirmarse.
 * 3. Un pedido confirmado no puede confirmarse otra vez.
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

    /**US2*/
    @Test
    void shouldAddOrderItemToDraftOrder() {
        // Arrange
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
        // Arrange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TEN;
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }

    @Test
    void shouldRejectNegativeUnitPrice(){
        // Arrange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = new  BigDecimal("-1");
        int quantity = 1;
        // Act & Assert
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
        // Arrange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();;
        BigDecimal unitPrice = null;
        int quantity = 1;
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }

    @Test
    void shouldRejectNullProductId(){
        // Arrange
        Order order = Order.create();
        ProductId productId = null;
        BigDecimal unitPrice = BigDecimal.TEN;
        int quantity = 1;
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }

    @Test
    void shouldCalculateOrderTotalPrice() {
        // Arrange
        Order order = Order.create();
        ProductId product1Id = ProductId.generateProductId();
        BigDecimal unitPrice1 = BigDecimal.ONE;
        int quantity1 = 3;

        ProductId product2Id = ProductId.generateProductId();
        BigDecimal unitPrice2 = BigDecimal.TWO;
        int quantity2 = 4;

        // Act
        order.addOrderItem(product1Id, unitPrice1, quantity1); // 3
        order.addOrderItem(product2Id, unitPrice2, quantity2); // 8

       BigDecimal orderTotalPrice = order.getTotalPrice();

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(11), orderTotalPrice);
    }
    @Test
    void shouldRejectAddingItemsToConfirmedOrder() {
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TWO;
        int quantity = 10;
        order.addOrderItem(productId, unitPrice, quantity);

        // Act
        order.confirm();

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, () -> {
            order.addOrderItem(productId, unitPrice, quantity);
        });
    }
/**US3*/
    @Test
    void shouldConfirmDraftOrderWithItems() {
        // Arrange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TWO;
        int quantity = 10;
        order.addOrderItem(productId, unitPrice, quantity);

        // Act
        order.confirm();

        //Assert
        Assertions.assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldRejectConfirmingEmptyOrder() {
        // Arrange
        Order order = Order.create();

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, () -> {
            order.confirm();
        });
    }

    @Test
    void shouldRejectConfirmingAlreadyConfirmedOrder() {

        // Arrange
        Order order = Order.create();
        ProductId productId = ProductId.generateProductId();
        BigDecimal unitPrice = BigDecimal.TWO;
        int quantity = 10;
        order.addOrderItem(productId, unitPrice, quantity);
        order.confirm();

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, () -> {
            order.confirm();
        });

    }

/** User Story 3 — Confirmar pedidos
 * 1. Un pedido DRAFT con productos puede pasar a CONFIRMED.
 * 2. Un pedido vacío no puede confirmarse.
            * 3. Un pedido confirmado no puede confirmarse otra vez.
            */
}
