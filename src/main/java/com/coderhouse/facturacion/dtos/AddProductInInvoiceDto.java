package com.coderhouse.facturacion.dtos;

import java.util.List;

public class AddProductInInvoiceDto {

    private Long invoiceId;
    private List<Long> productId;


    
    public Long getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    public List<Long> getProductId() {
        return productId;
    }
    public void setProductId(List<Long> productId) {
        this.productId = productId;
    }

    

}
