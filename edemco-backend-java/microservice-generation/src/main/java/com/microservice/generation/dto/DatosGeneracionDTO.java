package com.microservice.generation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosGeneracionDTO {

    @JsonProperty("Monthly Cumulative Power generation")
    private Double monthlyCumulativePowerGeneration;

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth fechaFactura;
    private String plantName;
}
