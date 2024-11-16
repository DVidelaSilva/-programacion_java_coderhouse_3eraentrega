package com.coderhouse.facturacion.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.facturacion.dtos.AddProductInInvoiceDto;
import com.coderhouse.facturacion.models.Invoice;
import com.coderhouse.facturacion.models.InvoiceDetail;
import com.coderhouse.facturacion.models.Product;
import com.coderhouse.facturacion.repositories.InvoiceDetailRepository;
import com.coderhouse.facturacion.repositories.InvoiceRepository;
import com.coderhouse.facturacion.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;



    // Buscar todas los Detales de Facturas
    public List<InvoiceDetail> getAllInvoiceDetails() {
        return invoiceDetailRepository.findAll();
    }


    //Buscar los Detales de Facturas por ID 
    public InvoiceDetail findById(Long id){
        return invoiceDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("DetalleFactura no encontrada"));
    }

    

    // Buscar los productos asociados a una factura por su ID
    public List<Product> getProductsByInvoiceId(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoice(invoice);

        List<Product> products = new ArrayList<>();

        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            products.add(invoiceDetail.getProduct());
        }

        return products;
    }


    // Agregar productos a una Factura por ID
    @Transactional
    public List<InvoiceDetail> addProductInInvoice(AddProductInInvoiceDto addProductInInvoiceDto) {

        Invoice invoice = invoiceRepository.findById(addProductInInvoiceDto.getInvoiceId()).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        Map<Long, Long> productCountMap = new HashMap<>(); 

        for (Long productId : addProductInInvoiceDto.getProductId()){
            productCountMap.put(productId, productCountMap.getOrDefault(productId, 0L) + 1);
        }

        List<InvoiceDetail> invoiceDetails = new ArrayList<>();

        // Variable para acumular el total de la factura
        double totalInvoice = 0.0;

        for(Map.Entry<Long, Long> entry : productCountMap.entrySet()){
            Long productId = entry.getKey();
            Long count = entry.getValue();
        
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            InvoiceDetail invoiceDetail = new InvoiceDetail();

            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setProduct(product);
            invoiceDetail.setAmount(count);
            invoiceDetail.setPrice(product.getPrice());

            // Calcular el total de este producto y sumarlo al total de la factura
            double productTotal = product.getPrice() * count;
            totalInvoice += productTotal;

            invoiceDetailRepository.save(invoiceDetail);

            invoiceDetails.add(invoiceDetail);

        }

        invoice.setTotal(totalInvoice);
        invoiceRepository.save(invoice);

        return invoiceDetails;
    }


    // Actualizar un Detalle de Factura (Amount)
    @Transactional
    public InvoiceDetail updateInvoiceDetail(Long id, InvoiceDetail invoiceDetails){

        InvoiceDetail existingInvoiceDetail = invoiceDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));


        if (invoiceDetails.getAmount() != null && invoiceDetails.getAmount() > 0) {
            existingInvoiceDetail.setAmount(invoiceDetails.getAmount());
        }

        return invoiceDetailRepository.save(existingInvoiceDetail);
    }



    // Eliminar un Detalle de Factura 
    public void deleteInvoiceDetail(Long id){
        if(!invoiceDetailRepository.existsById(id)){
            throw new IllegalArgumentException("Factura no encontrada");
        } else {
            invoiceDetailRepository.deleteById(id);
        }
    }

}
