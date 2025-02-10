package com.xideral.gestion_usuarios_be.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETERS(HttpStatus.BAD_REQUEST, "INVALID_PARAMETERS"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),
    DUPLICATE_ELEMENT(HttpStatus.CONFLICT, "DUPLICATE_ELEMENT");

    private final HttpStatus status;
    private final String code;
}
