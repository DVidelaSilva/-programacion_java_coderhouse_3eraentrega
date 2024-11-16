package com.coderhouse.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.facturacion.apis.TimeResponseApi;
import com.coderhouse.facturacion.dtos.TimeResponseDto;
import com.coderhouse.facturacion.interfaces.TimeResponseInterface;

@Service
public class TimeResponseService implements TimeResponseInterface{

    @Autowired
    private TimeResponseApi timeResponseApi;

    @Override
    public TimeResponseDto getAll() {
        
        return timeResponseApi.getAll();
    }

}
