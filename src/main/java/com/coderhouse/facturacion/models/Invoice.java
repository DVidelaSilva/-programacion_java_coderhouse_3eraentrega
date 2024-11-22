package com.coderhouse.facturacion.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Modelo de Factura")
@Entity
@Table(name = "invoice")
@Data
public class Invoice {

    @Schema(description = "ID del factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "client", requiredMode = Schema.RequiredMode.REQUIRED, example = "Lista de clientes")
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Schema(description = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-11-16T22:13")
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Schema(description = "total", requiredMode = Schema.RequiredMode.REQUIRED, example = "100000")
    @Column(name = "total")
    private double total;

    @Schema(description = "details", requiredMode = Schema.RequiredMode.REQUIRED, example = "details de Factura")
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvoiceDetail> details;



}
