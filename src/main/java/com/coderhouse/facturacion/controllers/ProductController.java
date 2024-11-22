package com.coderhouse.facturacion.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.facturacion.dtos.ErrorResponse;
import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.models.Product;
import com.coderhouse.facturacion.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Gestión de Productos", description = "Endpoints con el CRUD para gestionar productos")
public class ProductController {

    @Autowired
    private ProductService productService;



    // Buscar todos los productos
    // Docu
    @Operation(summary = "Obtener un listado con todos los productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(){
        try {

            List<Product> products = productService.getAllProducts();

            return ResponseEntity.ok(products);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Buscar producto por ID
    // Docu
    @Operation(summary = "Obtener un listado de un producto segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto obtenido Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        try {
            
            Product product = productService.findById(id);

            return ResponseEntity.ok(product);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //Crear un producto 
     // Docu
     @Operation(summary = "Crear un nuevo producto")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "201", description = "Producto Creado Exitosamente",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)),
         }),
         @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
         }),
         @ApiResponse(responseCode = "409", description = "Conflicto. Registro duplicado",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
         })
     })
     // Metodo
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
                    return ResponseEntity.badRequest().body(errorMessages);
            }

            Product createdProduct = productService.saveProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: El producto ya se encuentra registrado en la BD");
        }
    }



    // Actualizar a un producto
     // Docu
     @Operation(summary = "Actualizar un parametro de un producto segun su ID")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Actualizacion realizada Exitosamente",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)),
         }),
         @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
         }),
         @ApiResponse(responseCode = "404", description = "Producto no encontrado",
         content = {
             @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
         })
     })
     // Metodo
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody @Valid  Product productDetails, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
                    return ResponseEntity.badRequest().body(errorMessages);
            }

            Product updateProduct = productService.updateProduct(id, productDetails);

            return ResponseEntity.ok(updateProduct);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // Eliminar un producto
    // Docu
    @Operation(summary = "Eliminar un producto segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        }),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
        })
    })
    // Metodo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try {

            productService.deleteProduct(id);
            
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
