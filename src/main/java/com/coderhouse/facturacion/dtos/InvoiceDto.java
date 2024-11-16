package com.coderhouse.facturacion.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para estructura ingreso parametros, agregar productos a factura")
@Data
public class InvoiceDto {

    @Schema(description = "invoiceId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long invoiceId;
    @Schema(description = "productId", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 1, 2, 2]")
    private List<Long> productId;

}
