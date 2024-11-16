package com.coderhouse.facturacion.dtos;

import lombok.Data;

@Data
public class LineaDto {

    private Long cantidad;
    private ProductoDto producto;

}
