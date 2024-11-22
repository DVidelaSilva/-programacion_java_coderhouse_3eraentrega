package com.coderhouse.facturacion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.facturacion.dtos.ErrorResponse;
import com.coderhouse.facturacion.dtos.RespuestaDto;
import com.coderhouse.facturacion.dtos.VoucherDto;
import com.coderhouse.facturacion.models.Invoice;
import com.coderhouse.facturacion.services.InvoiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Gestión de Facturas", description = "Endpoints con el CRUD para gestionar el detalle de facturas")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    // Buscar todas las facturas
    // Docu
    @Operation(summary = "Obtener un listado con todas ls facturas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoice(){
        try {

            List<Invoice> invoices = invoiceService.getAllInvoices();

            return ResponseEntity.ok(invoices);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Buscar factura por ID
    @GetMapping("/{id}")
    // Docu
    @Operation(summary = "Obtener una factura segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id){
        try {
            
            Invoice invoice = invoiceService.findById(id);

            return ResponseEntity.ok(invoice);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Buscar Facturas del cliente
    // Docu
    @Operation(summary = "Obtener una factura segun el ID del Cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Invoice>> getInvoiceBuClientId(@PathVariable Long clientId){
        try {

            List<Invoice> invoices = invoiceService.getInvoicesByClientId(clientId);

            if(invoices.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(invoices);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Crear una factura a un cliente
     // Docu
     @Operation(summary = "Crear una nueva factura a un cliente por su clienteID")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "201", description = "Factura Creada Exitosamente",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
         }),
         @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
         })
     })
     // Metodo
    @PostMapping("/create/{clientId}")
    public ResponseEntity<Invoice> createInvoiceForClient(@PathVariable Long clientId, @RequestBody Invoice invoice) {
        try {

            Invoice createdInvoice = invoiceService.createInvoiceToClient(clientId, invoice);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // Actualizar una factura
    // Docu
    @Operation(summary = "Actualizar un parametro de una factura segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizacion realizada Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceDetails){
        try {

            Invoice updateInvoice = invoiceService.updateInvoice(id, invoiceDetails);

            return ResponseEntity.ok(updateInvoice);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // Eliminar una factura
    // Docu
    @Operation(summary = "Eliminar una factura segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura eliminada Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id){
        try {

            invoiceService.deleteInvoice(id);
            
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    


    
    // Crea el Comprobante al Cliente agregando Productos
    // Docu
    @Operation(summary = "Crear un nuevo Voucher(Factura) por el clientID añadiendo producto mediente el productID y persistiendo en el detalle de Factura")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Voucher Creado Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @PostMapping("/voucher")
    public ResponseEntity<Object> postVoucher(@RequestBody VoucherDto voucherDto){
        try {

            RespuestaDto respuestaDto = invoiceService.voucher(voucherDto);
    
            return ResponseEntity.ok(respuestaDto);
            
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Error: " + e.getMessage()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error Inesperado: " + e.getMessage()));
            }
        }

}
