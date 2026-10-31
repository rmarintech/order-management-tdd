package com.rubenmarin.ordermanagementtdd.infrastructure.adapter.out.persistence;

import com.rubenmarin.ordermanagementtdd.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderJpaAdapterTest {

    @Mock
    OrderJpaRepository orderJpaRepository;

    @Mock
    OrderJpaMapper mapper;

    @InjectMocks
    OrderJpaAdapter orderJpaAdapter;

    @Test
    void shouldSaveOrder() {
        Order order = Order.create();

        OrderJpaEntity orderEntity = new OrderJpaEntity();

        Mockito.when(orderJpaRepository.save(orderEntity)).thenReturn(orderEntity);
        Mockito.when(mapper.toEntity(order)).thenReturn(orderEntity);

        orderJpaAdapter.save(order);

        Mockito.verify(mapper, Mockito.times(1)).toEntity(order);
        Mockito.verify(orderJpaRepository, Mockito.times(1)).save(orderEntity);


    }
}
