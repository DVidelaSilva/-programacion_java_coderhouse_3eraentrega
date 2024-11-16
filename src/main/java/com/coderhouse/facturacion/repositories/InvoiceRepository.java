package com.coderhouse.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.facturacion.models.Invoice;

public interface InvoiceRepository  extends JpaRepository<Invoice, Long>{

    List<Invoice> findByClientId(Long clienId);

}
