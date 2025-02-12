package com.xideral.gestion_usuarios_be.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.xideral.gestion_usuarios_be.dto.order.OrderDto;
import com.xideral.gestion_usuarios_be.entity.Order;
import com.xideral.gestion_usuarios_be.entity.User;
import com.xideral.gestion_usuarios_be.enums.OrderStatus;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import com.xideral.gestion_usuarios_be.repository.OrderRepository;
import com.xideral.gestion_usuarios_be.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getOrderById_Success() {

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();


        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDto orderDto = orderService.getOrderById(1L);

        assertEquals(orderDto.id(), order.getId());
        assertEquals(orderDto.userId(), order.getUser().getId());
        assertEquals(orderDto.status(), order.getStatus());
        assertEquals(orderDto.total(), order.getTotal());
        assertEquals(orderDto.dateCreated(), order.getDateCreated());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_Failed() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> orderService.getOrderById(1L));

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderByUserId_Success() {
        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();


        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        Order order2 = Order.builder()
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(12L)
                .dateCreated(new Date(123456789000L))
                .build();

        List<Order> orderList = List.of(order, order2);

        when(orderRepository.findOrdersByUser_Id(1L)).thenReturn(orderList);

        List<OrderDto> orderListResult = orderService.getOrderByUserId(1L);

        assertFalse(orderListResult.isEmpty());
        assertEquals(2, orderListResult.size());

        verify(orderRepository, times(1)).findOrdersByUser_Id(1L);

    }

    @Test
    void getOrderByUserId_Empty() {

        when(orderRepository.findOrdersByUser_Id(1L)).thenReturn(Collections.emptyList());

        List<OrderDto> orderListResult = orderService.getOrderByUserId(1L);

        assertTrue(orderListResult.isEmpty());

        verify(orderRepository, times(1)).findOrdersByUser_Id(1L);
    }

    @Test
    void createOrder_Success() {

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();

        Order orderCreated = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();


        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .userId(1L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(orderRepository.save(any(Order.class))).thenReturn(orderCreated);

        OrderDto result = orderService.createOrder(orderDto);

        assertAll("Validación del token",
                () ->  assertNotNull(result),
                () -> assertEquals(orderCreated.getId(), result.id()),
                () -> assertEquals(orderCreated.getUser().getId(), result.userId()),
                () -> assertEquals(orderCreated.getTotal(), result.total()),
                () -> assertEquals(orderCreated.getStatus(), result.status()),
                () -> assertEquals(orderCreated.getDateCreated(), result.dateCreated())

        );

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_Failed() {

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .userId(1L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> orderService.createOrder(orderDto));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void deleteOrder_Sucess() {

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrder_Failed() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> orderService.deleteOrder(1L));

        verify(orderRepository, never()).deleteById(1L);
    }

    @Test
    void updateOrder_Failed_IDNotFound() {

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .userId(1L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> orderService.updateOrder(1L, orderDto));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrder_Sucess() {

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .userId(1L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto orderCreate = orderService.updateOrder(1L, orderDto);

        assertEquals(orderCreate, orderDto);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_Sucess_ChangeUserId() {

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .userId(2L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        OrderDto orderDtoCreated = OrderDto.builder()
                .id(1L)
                .userId(1L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        OrderDto orderCreate = orderService.updateOrder(1L, orderDto);

        assertEquals(orderDtoCreated, orderCreate);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_Failed_UserNotExist() {

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .userId(2L)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDIENTE)
                .total(10L)
                .dateCreated(new Date(123456789000L))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> orderService.updateOrder(1L, orderDto));

        verify(orderRepository, never()).save(any(Order.class));
    }

}
