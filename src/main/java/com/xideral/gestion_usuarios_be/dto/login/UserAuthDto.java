package com.xideral.gestion_usuarios_be.dto.login;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserAuthDto(Long id, String user, String password) {
}
