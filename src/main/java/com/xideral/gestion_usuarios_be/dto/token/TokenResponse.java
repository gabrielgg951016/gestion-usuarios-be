package com.xideral.gestion_usuarios_be.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder(toBuilder = true)
public record TokenResponse(@JsonProperty("access_token") String accessToken){
}
