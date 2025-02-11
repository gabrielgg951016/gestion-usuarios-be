package com.xideral.gestion_usuarios_be.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xideral.gestion_usuarios_be.entity.Order;
import com.xideral.gestion_usuarios_be.enums.OrderStatus;
import lombok.Builder;
import lombok.NonNull;

import java.sql.Date;

@Builder(toBuilder = true)
public record OrderDto (Long id, @JsonProperty("user_id") Long userId, OrderStatus status, Long total,
                        @JsonProperty("date_created") Date dateCreated) {

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .status(order.getStatus())
                .total(order.getTotal())
                .dateCreated(order.getDateCreated())
                .build();
    }
}
