package com.xideral.gestion_usuarios_be.service;

import static com.xideral.gestion_usuarios_be.enums.ErrorCode.USER_NOT_FOUND;
import static com.xideral.gestion_usuarios_be.service.UserService.USER_NOT_EXIST_BY_ID;
import static java.lang.String.format;

import com.xideral.gestion_usuarios_be.dto.order.OrderDto;
import com.xideral.gestion_usuarios_be.entity.Order;
import com.xideral.gestion_usuarios_be.entity.User;
import com.xideral.gestion_usuarios_be.enums.ErrorCode;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import com.xideral.gestion_usuarios_be.repository.OrderRepository;
import com.xideral.gestion_usuarios_be.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    public static final String ORDER_NOT_EXIST = "El pedido con id %d no existe";

    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderDto getOrderById (Long id) {
        return OrderDto.from(orderRepository.findById(id)
                .orElseThrow(() -> new UseCaseException(ErrorCode.ORDER_NOT_FOUND, format(ORDER_NOT_EXIST, id))));
    }

    public List<OrderDto> getOrderByUserId (Long userId) {
        return orderRepository.findOrdersByUser_Id(userId).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    public OrderDto createOrder (OrderDto orderDto) {

        final Optional<User> user = userRepository.findById(orderDto.userId());

        if(user.isEmpty()) {
            throw new UseCaseException(USER_NOT_FOUND, format(USER_NOT_EXIST_BY_ID, orderDto.userId()));
        }

        return OrderDto.from(orderRepository.save(
                Order.builder()
                        .user(user.get())
                        .status(orderDto.status())
                        .total(orderDto.total())
                        .dateCreated(orderDto.dateCreated())
                        .build()
        ));
    }

    public OrderDto updateOrder (Long id,  OrderDto orderDto) {

        final Optional<Order> order = orderRepository.findById(id);

        if(order.isEmpty()) {
            throw new UseCaseException(USER_NOT_FOUND, format(ORDER_NOT_EXIST, id));
        }

        boolean usersIdAreDifferent = orderDto.userId().equals(order.get().getUser().getId());

        final Optional<User> user;

        if(!usersIdAreDifferent) {
            user = userRepository.findById(orderDto.userId());

            if(user.isEmpty()) {
                throw new UseCaseException(USER_NOT_FOUND, format(USER_NOT_EXIST_BY_ID, orderDto.userId()));
            }

            order.get().toBuilder()
                    .user(user.get())
                    .build();
        }

        return OrderDto.from(orderRepository.save(
                order.get().toBuilder()
                        .status(orderDto.status())
                        .total(orderDto.total())
                        .dateCreated(orderDto.dateCreated())
                        .build()
        ));
    }

    public void deleteOrder (Long id) {

        final Optional<Order> order = orderRepository.findById(id);

        if(order.isEmpty()) {
            throw new UseCaseException(USER_NOT_FOUND, format(ORDER_NOT_EXIST, id));
        }

        orderRepository.deleteById(id);
    }
}
