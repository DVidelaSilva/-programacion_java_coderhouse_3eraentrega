package com.coderhouse.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.facturacion.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
