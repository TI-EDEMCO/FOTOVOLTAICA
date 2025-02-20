package com.IntegracionSiesa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class PlantaDto {

    private String idPlanta;
    private String asunto;
    private String centroCosto;
    private String nombrePlanta;
    private String urlImg;
    private Long idOperador;
    private Long idCliente;
    private Double valorUnidad;
    private Double porcentajeAumento;

    public PlantaDto(String idPlanta, String asunto, String centroCosto, String nombrePlanta, String urlImg, Long idOperador, Long idCliente, Double valorUnidad, Double porcentajeAumento) {
        this.idPlanta = idPlanta;
        this.asunto = asunto;
        this.centroCosto = centroCosto;
        this.nombrePlanta = nombrePlanta;
        this.urlImg = urlImg;
        this.idOperador = idOperador;
        this.idCliente = idCliente;
        this.valorUnidad = valorUnidad;
        this.porcentajeAumento = porcentajeAumento;
    }
}
