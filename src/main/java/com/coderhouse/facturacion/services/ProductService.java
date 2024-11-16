package com.coderhouse.facturacion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.coderhouse.facturacion.models.Product;
import com.coderhouse.facturacion.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    // Buscar todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


     //Buscar producto por ID 
    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }


    //Crear un producto 
    @Transactional
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }



    // Actualizar a un producto
    @Transactional
    public Product updateProduct(Long id, Product productDetails){

        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        
        if(productDetails.getCode() != null && !productDetails.getCode().isEmpty()){
            product.setCode(productDetails.getCode());
        }

        return productRepository.save(product);
    }



    // Eliminar un producto
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new IllegalArgumentException("Producto no encontrado");
        } else {
            productRepository.deleteById(id);
        }
    }

}
