package com.coderhouse.facturacion.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Modelo de Produto")
@Entity
@Table(name = "products")
@Data
public class Product {

    @Schema(description = "productoId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "description", requiredMode = Schema.RequiredMode.REQUIRED, example = "celular marca x")
    @Column(name = "description", length = 150, nullable = false)
    private String description;

    @Schema(description = "code", requiredMode = Schema.RequiredMode.REQUIRED, example = "code0000")
    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Schema(description = "stock", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @Column(name="stock")
    private Long stock;

    @Schema(description = "price", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @Column(name="price")
    private double price;

    @Schema(description = "invoiceDetails", requiredMode = Schema.RequiredMode.REQUIRED, example = "Lista de detalle de factura")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails;

}
