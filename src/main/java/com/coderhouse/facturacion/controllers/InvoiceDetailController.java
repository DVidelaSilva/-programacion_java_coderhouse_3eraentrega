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

import com.coderhouse.facturacion.dtos.AddProductInInvoiceDto;
import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.models.InvoiceDetail;
import com.coderhouse.facturacion.models.Product;
import com.coderhouse.facturacion.services.InvoiceDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/invoice-details")
@Tag(name = "Gestión de detalle de facturas", description = "Endpoints con el CRUD para gestionar detalle de facturas")
public class InvoiceDetailController {
 
    @Autowired
    public InvoiceDetailService invoiceDetailService;




    // Buscar todos los Detalles de facturas
        // Docu
    @Operation(summary = "Obtener un listado con todos los detalles de facturas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de detalles de facturas obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        })
    })
    // Metodo
    @GetMapping
    public ResponseEntity<List<InvoiceDetail>> getAllInvoiceDetails(){
        try {

            List<InvoiceDetail> invoiceDetails = invoiceDetailService.getAllInvoiceDetails();

            return ResponseEntity.ok(invoiceDetails);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Buscar Detalles de facturas por ID
    // Docu
    @Operation(summary = "Obtener un detalle de factura segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "detalle de factura obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "404", description = "detalle de factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        })
    })
    // Metodo
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetail> getInvoiceDetailById(@PathVariable Long id){
        try {
            
            InvoiceDetail invoiceDetail = invoiceDetailService.findById(id);

            return ResponseEntity.ok(invoiceDetail);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // Busca los productos asociados a una factura
    // Docu
    @Operation(summary = "Obtener todos los producto asociados a una factura segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "detalle de factura obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "404", description = "detalle de factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        })
    })
    // Metodo
    @GetMapping("/{invoiceId}/products")
    public List<Product> getProductsByInvoiceId(@PathVariable Long invoiceId) {
        return invoiceDetailService.getProductsByInvoiceId(invoiceId);
    }



    
    //Agregar Productos a las Facturas (pasando InvoiceId y los ProductId)
    // Docu
    @Operation(summary = "Agregar Productos a las Facturas (pasando InvoiceId y los ProductId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "detalle de factura Creado Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        })
    })
    // Metodo
    @PostMapping
    public ResponseEntity<List<InvoiceDetail>> AddProductToInvoice(@RequestBody AddProductInInvoiceDto addProductInInvoiceDto){
        try {

        List<InvoiceDetail> invoiceDetails = invoiceDetailService.addProductInInvoice(addProductInInvoiceDto);

        return ResponseEntity.ok(invoiceDetails);
        
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    // Actualizar Detalles de facturas (amount)
    // Docu
    @Operation(summary = "Actualizar un parametro de un detalle de factura segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizacion realizada Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        }),
        @ApiResponse(responseCode = "404", description = "detalle de factura no encontrada",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
        })
    })
    // Metodo
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDetail> updateInvoiceDetail(@PathVariable Long id, @RequestBody InvoiceDetail invoiceDetails){
        try {

            InvoiceDetail updateInvoiceDetail = invoiceDetailService.updateInvoiceDetail(id, invoiceDetails);

            return ResponseEntity.ok(updateInvoiceDetail);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    // Eliminar Detalles de facturas
     // Docu
     @Operation(summary = "Eliminar un detalle de factura segun su ID")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "204", description = "detalle de factura eliminado Exitosamente",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
         }),
         @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
         }),
         @ApiResponse(responseCode = "404", description = "detalle de factura no encontrado",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDetail.class)),
         })
     })
     // Metodo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceDetail(@PathVariable Long id){
        try {

            invoiceDetailService.deleteInvoiceDetail(id);
            
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}