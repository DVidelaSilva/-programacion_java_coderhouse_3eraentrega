package com.coderhouse.facturacion.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para mensajes de error personalizados")
@Data
public class ErrorResponse {

    @Schema(description = "message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    // Constructor
    public ErrorResponse(String message) {
        this.message = message;
    }
}
