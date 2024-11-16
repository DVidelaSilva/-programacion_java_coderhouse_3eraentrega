package com.coderhouse.facturacion.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para estructura de voucher (Linea)")
@Data
public class LineaDto {

    @Schema(description = "cantidad", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long cantidad;
    @Schema(description = "producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "(modelo productDto)")
    private ProductoDto producto;

}
