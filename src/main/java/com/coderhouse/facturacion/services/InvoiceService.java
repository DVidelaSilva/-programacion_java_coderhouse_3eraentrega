package com.coderhouse.facturacion.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.facturacion.apis.TimeResponseApi;
import com.coderhouse.facturacion.dtos.ClientDto;
import com.coderhouse.facturacion.dtos.LineaDto;
import com.coderhouse.facturacion.dtos.ProductoDto;
import com.coderhouse.facturacion.dtos.RespuestaDto;
import com.coderhouse.facturacion.dtos.TimeResponseDto;
import com.coderhouse.facturacion.dtos.VoucherDto;
import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.models.Invoice;
import com.coderhouse.facturacion.models.InvoiceDetail;
import com.coderhouse.facturacion.models.Product;
import com.coderhouse.facturacion.repositories.ClientRepository;
import com.coderhouse.facturacion.repositories.InvoiceDetailRepository;
import com.coderhouse.facturacion.repositories.InvoiceRepository;
import com.coderhouse.facturacion.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private TimeResponseApi timeResponseApi;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;


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


    @Transactional
    public RespuestaDto voucher(VoucherDto voucherDto) {

        // Buqueda & validacion del Client
        Client client = clientRepository.findById(voucherDto.getClientId()).orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + voucherDto.getClientId() + " No encontrado"));

        // Contador de las cantidades de procutos iguales en el array productId del Request
        Map<Long, Long> productCountMap = new HashMap<>(); 

        // Contador de productos
        for (Long productId : voucherDto.getProductId()){
            productCountMap.put(productId, productCountMap.getOrDefault(productId, 0L) + 1);
        }

        // Nuevo array para lineas de productos
        List<LineaDto> arrayLineaDtos = new ArrayList<>();

        // Inicializador de variables Total Voucher (monto) y total productos en voucher
        double totalInvoice = 0.0;
        int totalProduct = 0; 

        // Nuevo array para detalle de productos en el voucher
        List<InvoiceDetail> arrayInvoiceDetail = new ArrayList<>();

        // Recorrer los productos y verificar stock
        for(Map.Entry<Long, Long> entry : productCountMap.entrySet()){
            Long productId = entry.getKey();
            Long count = entry.getValue();

            // Buqueda & validacion del Product
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Producto con ID " + productId + " no encontrado"));

            // Verificar si el stock es suficiente
            if(count > product.getStock()){
                throw new IllegalArgumentException("Producto con ID " + productId + " no tiene suficiente Stock");
            } 

            // DTO respuesta
            LineaDto lineaDto = new LineaDto();
            lineaDto.setCantidad(count);

            ProductoDto productoDto = new ProductoDto();
            productoDto.setProductoId(product.getId());
            
            lineaDto.setProducto(productoDto);
            arrayLineaDtos.add(lineaDto);

            // Calcular el total para este producto
            double productTotal = product.getPrice() * count;
            totalInvoice += productTotal;

           // Calcular el total de productos vendidos (solo la cantidad)
            totalProduct += count;  

            // Crear el detalle de la factura (InvoiceDetail)
            InvoiceDetail newInvoiceDetail = new InvoiceDetail();
            newInvoiceDetail.setAmount(count);
            newInvoiceDetail.setPrice(product.getPrice());
            newInvoiceDetail.setProduct(product);
            // Añadir el detalle de la factura a la lista
            arrayInvoiceDetail.add(newInvoiceDetail);

             // Restar el stock del producto
            product.setStock(product.getStock() - count);
            productRepository.save(product); 

        }

        // Creacion nuevo Voucher
        Invoice newInvoice = new Invoice();
        newInvoice.setClient(client);
        newInvoice.setCreatedAt(LocalDateTime.now());
        newInvoice.setTotal(0.0);
        invoiceRepository.save(newInvoice);

        // factura a cada detalle
        for(InvoiceDetail invoiceDetail : arrayInvoiceDetail){
            invoiceDetail.setInvoice(newInvoice);
            invoiceDetailRepository.save(invoiceDetail);
        }

        // Actualizar el total de la factura
        newInvoice.setTotal(totalInvoice);
        invoiceRepository.save(newInvoice);
       

        // Asociar API Fecha
        TimeResponseDto timeResponseDto = null;
        try {
             timeResponseDto = timeResponseApi.getAll();
        } catch (Exception e) {
            System.err.println("Error al obtener la fecha de la APi, se Utilizara la fecha actual ");
        }
        String currentDateTime = null;
        if(timeResponseDto != null && timeResponseDto.getCurrentDateTime() != null ) {
            try {
                    // Adaptar Formato fecha
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    LocalDateTime apiDateTime = LocalDateTime.parse(timeResponseDto.getCurrentDateTime(), formatter);
                    currentDateTime = "TimeAPI :: " + apiDateTime.toString();
            } catch (Exception e) {
                System.err.println("Error al parsear la fecha de la APi, se Utilizara la fecha actual ");
                currentDateTime = LocalDateTime.now().toString();
            }
        } else {
            currentDateTime = "TimeLocal :: " +  LocalDateTime.now().toString();
        }

        // Crear la respuesta (Response)
        RespuestaDto respuestaDto = new RespuestaDto();
        ClientDto clientDto = new ClientDto();
        clientDto.setClienteid(client.getId());
        respuestaDto.setClient(clientDto);
        respuestaDto.setLineas(arrayLineaDtos);
        respuestaDto.setTotal_product(totalProduct);
        respuestaDto.setTotal_Sale(totalInvoice);
        respuestaDto.setCreated_at(currentDateTime);

        return respuestaDto;
    } 
}
