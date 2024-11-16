package com.coderhouse.facturacion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.models.Invoice;
import com.coderhouse.facturacion.repositories.ClientRepository;
import com.coderhouse.facturacion.repositories.InvoiceRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ClientRepository clientRepository;


    // Buscar todas las Facturas
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }


    //Buscar una factura por ID 
    public Invoice findById(Long id){
        return invoiceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
    }


    // Buscar facturas del cliente
    public List<Invoice> getInvoicesByClientId(Long clientId){
        
        return invoiceRepository.findByClientId(clientId);
    }



    //Crear una factura a un cliente
    @Transactional 
    public Invoice createInvoiceToClient(Long clienId, Invoice invoice) {

        Client client = clientRepository.findById(clienId).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Invoice newInvoice = new Invoice();
        
        newInvoice.setTotal(invoice.getTotal());
        newInvoice.setClient(client);

        return invoiceRepository.save(newInvoice);
    }


    // Actualizar una factura
    @Transactional
    public Invoice updateInvoice(Long id, Invoice invoiceDetails){

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if(invoiceDetails.getTotal() != 0){
            invoice.setTotal(invoiceDetails.getTotal());
        }

        return invoiceRepository.save(invoice);
    }


    // Eliminar una factura
    public void deleteInvoice(Long id){
        if(!invoiceRepository.existsById(id)){
            throw new IllegalArgumentException("Factura no encontrada");
        } else {
            invoiceRepository.deleteById(id);
        }
    }

}
