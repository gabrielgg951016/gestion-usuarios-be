package com.xideral.gestion_usuarios_be.config;

import static com.xideral.gestion_usuarios_be.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.xideral.gestion_usuarios_be.enums.ErrorCode.INVALID_PARAMETERS;
import static com.xideral.gestion_usuarios_be.enums.ErrorCode.RESOURCE_NOT_FOUND;

import com.xideral.gestion_usuarios_be.exception.ApiError;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UseCaseException.class)
    public ResponseEntity<ApiError> useCaseExceptionHandler(UseCaseException e) {
        ApiError apiError = e.toApiError();
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(apiError);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> methodValidationExceptionHandler(HandlerMethodValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(e.hashCode())
                        .message("One or more fields have invalid values.")
                        .errorCode(INVALID_PARAMETERS.getCode())
                        .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(e.hashCode())
                        .message("The value provided for one or more parameters is not of the expected type.")
                        .errorCode(INVALID_PARAMETERS.getCode())
                        .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> noResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(e.hashCode())
                        .message("Resource not found")
                        .errorCode(RESOURCE_NOT_FOUND.getCode())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> apiException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .status(INTERNAL_SERVER_ERROR.getStatus().value())
                        .message(e.getMessage())
                        .errorCode(INTERNAL_SERVER_ERROR.getCode())
                        .build());
    }
}

