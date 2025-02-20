package com.microservice.generation.controller.sto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarifaOperadorDto {

    private Long idTarifa;
    private Double tarifaOperador;
    private Integer mes;
    private Integer anio;
    private Long idOperador;
}
