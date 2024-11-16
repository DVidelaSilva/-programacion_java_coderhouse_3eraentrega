package com.coderhouse.facturacion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.facturacion.dtos.TimeResponseDto;
import com.coderhouse.facturacion.models.Client;
import com.coderhouse.facturacion.services.TimeResponseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/time")
public class TimeResponseController {

    @Autowired
    private TimeResponseService timeResponseService;


    // Docu
    @Operation(summary = "Obtener el dettale de tiempo desde API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informacion obtenida Exitosamente",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TimeResponseDto.class)),
        }),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TimeResponseDto.class)),
        })
    })
    // Metodo
    @GetMapping()
    public ResponseEntity<TimeResponseDto> getTime(){
        try {

            TimeResponseDto time = timeResponseService.getAll();

            return ResponseEntity.ok(time);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
