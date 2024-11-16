package com.coderhouse.facturacion.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.coderhouse.facturacion.dtos.TimeResponseDto;
import com.coderhouse.facturacion.interfaces.TimeResponseInterface;


@Component
public class TimeResponseApi implements TimeResponseInterface{

    private final String BASE_URL = "http://worldclockapi.com/api/json/utc/now";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TimeResponseDto getAll() {
        try {
            
            TimeResponseDto time = restTemplate.getForObject(BASE_URL, TimeResponseDto.class);
            return time;
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
        // Loguea el cuerpo de la respuesta de error
        System.err.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        throw new RuntimeException("Error al obtener el Time: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            throw new RuntimeException("Error al obtener el Time: " + e.getMessage(), e);
        }


    }

    

}
