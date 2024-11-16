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
import com.coderhouse.facturacion.dtos.ErrorResponse;
import com.coderhouse.facturacion.dtos.InvoiceDto;
import com.coderhouse.facturacion.dtos.RespuestaDto;
import com.coderhouse.facturacion.dtos.VoucherDto;
import com.coderhouse.facturacion.models.InvoiceDetail;
import com.coderhouse.facturacion.models.Product;
import com.coderhouse.facturacion.services.InvoiceDetailService;



@RestController
@RequestMapping("/api/invoice-details")
public class InvoiceDetailController {
 
    @Autowired
    public InvoiceDetailService invoiceDetailService;



    // Buscar todos los Detalles de facturas
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
    @GetMapping("/{invoiceId}/products")
    public List<Product> getProductsByInvoiceId(@PathVariable Long invoiceId) {
        return invoiceDetailService.getProductsByInvoiceId(invoiceId);
    }



    



    //Agregar Productos a las Facturas
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