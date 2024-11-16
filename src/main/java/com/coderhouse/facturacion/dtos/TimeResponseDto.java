package com.coderhouse.facturacion.dtos;

import lombok.Data;

@Data
public class TimeResponseDto {


    private String CurrentDateTime;
    private String utcOffset;
    private Boolean isDayLightSavingsTime;
    private String dayOfTheWeek;
    private String timeZoneName;
    private long currentFileTime;
    private String ordinalDate;


}
