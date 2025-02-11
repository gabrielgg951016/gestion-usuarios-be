package com.xideral.gestion_usuarios_be.controller;

import static com.xideral.gestion_usuarios_be.controller.UserController.validateDate;
import static com.xideral.gestion_usuarios_be.controller.UserController.validateId;

import com.xideral.gestion_usuarios_be.dto.order.OrderDto;
import com.xideral.gestion_usuarios_be.dto.user.UserDto;
import com.xideral.gestion_usuarios_be.enums.ErrorCode;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import com.xideral.gestion_usuarios_be.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Controller
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        validateId(id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        validateId(userId);
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        validateOrder(orderDto);
        validateId(orderDto.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {

        validateId(id);
        validateId(orderDto.id());

        if(!id.equals(orderDto.id())) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El ID debe coincidir en el path y en el body");
        }

        validateOrder(orderDto);
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteOrderById(@PathVariable Long id) {
        validateId(id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    private static void validateOrder(OrderDto orderDto) {
        if (orderDto.total() == null || orderDto.total() <= 0) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El total debe ser un número positivo");
        }

        validateDate(orderDto.dateCreated());
    }

}
