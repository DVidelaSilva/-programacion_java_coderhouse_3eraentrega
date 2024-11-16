package com.coderhouse.facturacion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.facturacion.dtos.TimeResponseDto;
import com.coderhouse.facturacion.services.TimeResponseService;

@RestController
@RequestMapping("/api/time")
public class TimeResponseController {

    @Autowired
    private TimeResponseService timeResponseService;

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
