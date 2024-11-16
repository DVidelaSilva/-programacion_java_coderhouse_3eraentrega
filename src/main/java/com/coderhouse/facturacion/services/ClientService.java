package com.coderhouse.facturacion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.repositories.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    // Buscar todos los clientes
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }


    //Buscar cliente por ID 
    public Client findById(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }


    //Crear un cliente 
    @Transactional
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }


    // Actualizar a un cliente
    @Transactional
    public Client updateClient(Long id, Client clientDetails){

        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        client.setFirstName(clientDetails.getFirstName());
        client.setLastName(clientDetails.getLastName());
        
        if(clientDetails.getDocNumber() != null && !clientDetails.getDocNumber().isEmpty()){
            client.setDocNumber(clientDetails.getDocNumber());
        }

        return clientRepository.save(client);
    }


    // Eliminar un cliente
    public void deleteClient(Long id){
        if(!clientRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente no encontrado");
        } else {
            clientRepository.deleteById(id);
        }
    }

}
