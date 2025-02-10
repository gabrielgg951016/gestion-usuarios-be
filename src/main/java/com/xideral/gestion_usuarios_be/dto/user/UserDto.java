package com.xideral.gestion_usuarios_be.dto.user;

import com.xideral.gestion_usuarios_be.entity.User;
import lombok.Builder;

import java.io.Serial;
import java.sql.Date;

@Builder(toBuilder = true)
public record UserDto(Long id, String name, String email, Date dateCreated) {

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dateCreated(user.getDateCreated())
                .build();
    }
}
