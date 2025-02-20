package com.microservice.facturacion_especial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturacionEspecialDTO {

    private Long idFacturacionEspecial;
    private Float cantidadkWh;
    private Float excedente;
    private Float costoAgregado;
    private String idPlanta;

}
