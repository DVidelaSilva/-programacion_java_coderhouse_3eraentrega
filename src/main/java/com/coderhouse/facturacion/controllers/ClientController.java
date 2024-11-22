package com.coderhouse.facturacion.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.facturacion.dtos.ErrorResponse;
import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/clients")
@Tag(name = "Gestión de Clientes", description = "Endpoints con el CRUD para gestionar clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;


    // Buscar todos los clientes
    // Docu
    @Operation(summary = "Obtener un listado con todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de Clientes obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        try {

            List<Client> clients = clientService.getAllClients();

            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Buscar cliente por ID
    // Docu
    @Operation(summary = "Obtener un cliente segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente obtenido Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id){
        try {
            
            Client client = clientService.findById(id);

            return ResponseEntity.ok(client);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Crear un cliente 
    // Docu
    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente Creado Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "409", description = "Conflicto. Registro duplicado",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Valid Client client, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
                    return ResponseEntity.badRequest().body(errorMessages);
            }
            Client createdClient = clientService.saveClient(client);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: El cliente ya se encuentra registrado en la BD");
        }
    }


    
    // Actualizar a un cliente
    // Docu
    @Operation(summary = "Actualizar un parametro de un cliente segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizacion realizada Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody @Valid Client clientDetails, BindingResult result){
        try {

            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
                    return ResponseEntity.badRequest().body(errorMessages);
            }
            Client updateClient = clientService.updateClient(id, clientDetails);
            return ResponseEntity.ok(updateClient);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
        
    



     // Eliminar un cliente
     // Docu
    @Operation(summary = "Eliminar un cliente segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        try {

            clientService.deleteClient(id);

            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
