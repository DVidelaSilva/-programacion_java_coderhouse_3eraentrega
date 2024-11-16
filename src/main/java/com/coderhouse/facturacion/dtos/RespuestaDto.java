package com.coderhouse.facturacion.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "DTO para estructura de voucher ")
@Data
public class RespuestaDto {

    @Schema(description = "client", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private ClientDto client;
    @Schema(description = "lineas", requiredMode = Schema.RequiredMode.REQUIRED, example = "(LineaDto)")
    private List<LineaDto> lineas;
    @Schema(description = "total_product", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private int total_product;
    @Schema(description = "total_Sale", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Double total_Sale;
    @Schema(description = "created_at", requiredMode = Schema.RequiredMode.REQUIRED, example = "TimeAPI :: 2024-11-16T22:01")
    private String created_at;

}
