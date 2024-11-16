package com.coderhouse.facturacion.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para estructura de voucher (producto)")
@Data
public class ProductoDto {

    @Schema(description = "productoId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long productoId;
}
