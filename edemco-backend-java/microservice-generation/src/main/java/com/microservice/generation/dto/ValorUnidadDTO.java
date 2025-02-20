package com.microservice.generation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValorUnidadDTO {

    private Double valorUnidad;
    private Integer mes;
    private Integer anio;

    public ValorUnidadDTO(Integer anio, Integer mes, Double valorUnidad) {
        this.anio = anio;
        this.mes = mes;
        this.valorUnidad = valorUnidad;
    }
}
