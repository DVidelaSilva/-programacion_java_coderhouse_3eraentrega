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



@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    // Buscar todas las facturas
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
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id){
        try {
            
            Invoice invoice = invoiceService.findById(id);

            return ResponseEntity.ok(invoice);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Buscar Facturas del cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Invoice>> getInvoiceBuClientId(@PathVariable Long clientId){
        try {

            List<Invoice> invoices = invoiceService.getInvoicesByClientId(clientId);

            if(invoices.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(invoices);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Crear una factura a un cliente
    @PostMapping("/create/{clientId}")
    public ResponseEntity<Invoice> createInvoiceForClient(@PathVariable Long clientId, @RequestBody Invoice invoice) {
        try {

            Invoice createdInvoice = invoiceService.createInvoiceToClient(clientId, invoice);

            return ResponseEntity.ok(createdInvoice);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // Actualizar una factura
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceDetails){
        try {

            Invoice updateInvoice = invoiceService.updateInvoice(id, invoiceDetails);

            return ResponseEntity.ok(updateInvoice);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // Eliminar una factura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id){
        try {

            invoiceService.deleteInvoice(id);
            
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    


    
    // Crea el Comprobante al Cliente agregando Productos
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
