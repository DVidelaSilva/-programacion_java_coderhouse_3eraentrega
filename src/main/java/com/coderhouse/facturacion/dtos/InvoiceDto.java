package com.coderhouse.facturacion.dtos;

import java.util.List;

import lombok.Data;

@Data
public class InvoiceDto {

    private Long invoiceId;
    private List<Long> productId;

}
