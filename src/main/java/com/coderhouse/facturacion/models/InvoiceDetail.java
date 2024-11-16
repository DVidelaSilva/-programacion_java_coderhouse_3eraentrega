package com.coderhouse.facturacion.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Schema(description = "Modelo de Detalle de Factura")
@Entity
@Table(name = "invoice_details")
@Data
public class InvoiceDetail {

    @Schema(description = "invoiceDetailId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceDetailId;

    @Schema(description = "invoiceId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Schema(description = "productid", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Schema(description = "amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Schema(description = "price", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @Column(name = "price", nullable = false)
    private double  price;



}
