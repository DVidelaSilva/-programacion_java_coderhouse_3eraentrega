package com.coderhouse.facturacion.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para estructura de voucher (request)")
@Data
public class VoucherDto {

    @Schema(description = "clientId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long clientId;
    @Schema(description = "productId", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 1, 2, 3]")
    private List<Long> productId;

}
