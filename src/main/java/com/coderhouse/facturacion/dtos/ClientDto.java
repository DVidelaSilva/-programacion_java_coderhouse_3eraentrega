package com.coderhouse.facturacion.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para estructura de voucher (client)")
@Data
public class ClientDto {

    @Schema(description = "clienteid", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long clienteid;

}
