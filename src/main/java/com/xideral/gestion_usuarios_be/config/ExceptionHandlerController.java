package com.xideral.gestion_usuarios_be.config;

import com.xideral.gestion_usuarios_be.exception.ApiError;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UseCaseException.class)
    public ResponseEntity<ApiError> useCaseExceptionHandler(UseCaseException e) {
        ApiError apiError = e.toApiError();
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(apiError);
    }
}
