package com.coderhouse.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.facturacion.models.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

}
