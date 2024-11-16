package com.coderhouse.facturacion.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	//http://localhost:8080/swagger-ui.html#
	//http://localhost:8080/swagger-ui/

	@Bean
	OpenAPI customOpenAPI () {
		return new OpenAPI()
				.info(new Info()
						.title("Programación Java | EntregaFinal - Facturación | CoderHouse")
						.version("3.0.0")
						.description("La API REST proporciona endpoints para administrar Clientes,  "
                        		+ "productos y facturas de un comercio. "
                        		+ "CRUD (Crear, Leer, Actualizar, Eliminar) tanto para clientes como "
                        		+ "para productos y facturas. Los endpoints permiten listar, agregar, mostrar, "
                        		+ "editar y eliminar clientes, productos y facturas. La API está documentada utilizando "
                        		+ "Swagger, lo que facilita la comprensión de los endpoints y su uso.")
						.contact(new Contact()
								.name("Diego Videla Silva")
								.email("dvidelasilva@gmail.com"))
						.license(new License()
								.name("Github")
								.url("https://github.com/DVidelaSilva/-programacion_java_coderhouse_3eraentrega"))
						)
						.servers(List.of(new Server()
								.url("http://localhost:8080")
								.description("Servidor Local")));
				
	}
	
}