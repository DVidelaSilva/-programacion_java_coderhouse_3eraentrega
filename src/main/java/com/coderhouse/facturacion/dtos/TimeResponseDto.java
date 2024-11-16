package com.coderhouse.facturacion.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "DTO para estructura API EXTERNA Time")
@Data
public class TimeResponseDto {

    @Schema(description = "CurrentDateTime", requiredMode = Schema.RequiredMode.REQUIRED, example = "00:00:00")
    private String CurrentDateTime;
    @Schema(description = "utcOffset", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private String utcOffset;
    @Schema(description = "isDayLightSavingsTime", requiredMode = Schema.RequiredMode.REQUIRED, example = "Saturday")
    private Boolean isDayLightSavingsTime;
    @Schema(description = "dayOfTheWeek", requiredMode = Schema.RequiredMode.REQUIRED, example = "UTC")
    private String dayOfTheWeek;
    @Schema(description = "timeZoneName", requiredMode = Schema.RequiredMode.REQUIRED, example = "133762687843623229")
    private String timeZoneName;
    @Schema(description = "currentFileTime", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-321")
    private long currentFileTime;
    @Schema(description = "ordinalDate", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-11-16T22:13Z")
    private String ordinalDate;


}
