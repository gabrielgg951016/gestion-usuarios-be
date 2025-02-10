package com.xideral.gestion_usuarios_be.exception;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    @JsonProperty
    @JsonAlias
    private String errorCode;
    private String message;
    private Integer status;
}
