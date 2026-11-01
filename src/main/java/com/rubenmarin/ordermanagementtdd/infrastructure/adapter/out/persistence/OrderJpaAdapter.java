package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.application.port.out.persistence.OrderRepository;
import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import com.rubenmarin.ordermanagementtdd.domain.model.OrderId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class OrderJpaAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderJpaMapper  orderJpaMapper;

    public OrderJpaAdapter(
            OrderJpaRepository orderJpaRepository,
            OrderJpaMapper  orderJpaMapper
    ) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderJpaMapper = orderJpaMapper;
    }

    @Override
    @Transactional(readOnly = true) //Mantiene abierta la transacción mientras el mapper convierte la entidad JPA al dominio
    public Optional<Order> findById(OrderId orderId) {
        Optional<OrderJpaEntity> found = orderJpaRepository.findById(orderId.orderId());
        Optional<Order> toRet = found.map(entity -> orderJpaMapper.toOrderDomain(entity));

        return toRet;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity saved = orderJpaRepository.save(orderJpaMapper.toOrderEntity(order));
        return orderJpaMapper.toOrderDomain(saved);
    }
}
