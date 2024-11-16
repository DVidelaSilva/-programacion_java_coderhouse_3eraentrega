package com.coderhouse.facturacion.dtos;

import java.util.List;

import lombok.Data;

@Data
public class RespuestaDto {

    private ClientDto client;
    private List<LineaDto> lineas;
    private int total_product;
    private Double total_Sale;
    private String created_at;

}
