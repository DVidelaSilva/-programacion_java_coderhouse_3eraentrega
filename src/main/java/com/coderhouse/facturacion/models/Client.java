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


@Schema(description = "Modelo de Cliente")
@Entity
@Table(name = "clients")
@Data
public class Client {

    @Schema(description = "ID del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "firstName del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Luis")
    @Column(name = "firstName", length = 75, nullable = false)
    private String firstName;

    @Schema(description = "lastName del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Perez")
    @Column(name = "lastName", length = 75, nullable = false)
    private String lastName;

    @Schema(description = "docNumber del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456789")
    @Column(name = "docNumber", length = 11, unique = true, nullable = false)
    private String docNumber;

    @Schema(description = "invoices", requiredMode = Schema.RequiredMode.REQUIRED, example = "Lista de facturas")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Invoice> invoices;

}
