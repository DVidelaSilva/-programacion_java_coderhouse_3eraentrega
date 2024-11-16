package com.coderhouse.facturacion.dtos;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;

    // Constructor
    public ErrorResponse(String message) {
        this.message = message;
    }
}
