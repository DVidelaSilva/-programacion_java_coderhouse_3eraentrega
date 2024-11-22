package com.coderhouse.facturacion.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @NotBlank(message = "El campo firstName no puede estar vacío")
    @Size(max = 75, message="El firstName excede la cantidad de caracteres Permitidos")
    @Pattern(regexp = "^[^0-9]*$", message = "El campo firstName solo puede contener un String")
    @Column(name = "firstName", length = 75, nullable = false)
    private String firstName;

    @Schema(description = "lastName del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Perez")
    @NotNull
    @NotBlank(message = "El campo lastName no puede estar vacío")
    @Size(max = 75, message="El lastName excede la cantidad de caracteres Permitidos")
    @Pattern(regexp = "^[^0-9]*$", message = "El campo lastName solo puede contener un String")
    @Column(name = "lastName", length = 75, nullable = false)
    private String lastName;

    @Schema(description = "docNumber del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456789")
    @NotBlank(message = "El campo docNumber no puede estar vacío")
    @Size(max = 11, message="El docNumber excede la cantidad de numeros Permitidos")
    @Pattern(regexp = "^[0-9]+$", message = "El docNumber solo puede contener dígitos")
    @Column(name = "docNumber", length = 11, unique = true, nullable = false)
    private String docNumber;

    @Schema(description = "invoices", requiredMode = Schema.RequiredMode.REQUIRED, example = "Lista de facturas")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Invoice> invoices;

}
