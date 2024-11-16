package com.coderhouse.facturacion.dtos;

import java.util.List;

import lombok.Data;

@Data
public class VoucherDto {

    private Long clientId;
    private List<Long> productId;

}
