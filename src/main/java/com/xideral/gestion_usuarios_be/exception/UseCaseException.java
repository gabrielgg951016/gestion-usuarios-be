package com.xideral.gestion_usuarios_be.exception;

import com.xideral.gestion_usuarios_be.enums.ErrorCode;
import lombok.Getter;

import java.io.Serial;

@Getter
public class UseCaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6850973676286161763L;

    private final ErrorCode errorCode;

    public UseCaseException(final ErrorCode errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiError toApiError() {
        return ApiError.builder()
                .errorCode(getErrorCode().getCode())
                .message(getMessage())
                .status(getErrorCode().getStatus().value())
                .build();
    }

}
