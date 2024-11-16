package com.coderhouse.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.facturacion.models.Invoice;
import com.coderhouse.facturacion.models.InvoiceDetail;

public interface InvoiceDetailRepository extends JpaRepository <InvoiceDetail, Long>{

    List<InvoiceDetail> findByInvoice(Invoice invoice);

}
