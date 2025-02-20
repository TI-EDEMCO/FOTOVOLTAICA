package com.microservice.generation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class GeneratorDTO {

    private Long idGeneracion;
    private Double generacionActual;
    private Double generacionAcumulado;
    private Double valorUnidad;
    private Double valorTotal;
    private Double diferenciaTarifa;
    private Double ahorroActual;
    private Double ahorroAcumulado;
    private Double ahorroCodosActual;
    private Double ahorroCodosAcumulado;
    private Integer anio;
    private Integer mes;
    private Long idTarifaOperador;
    private String idPlanta;

    public GeneratorDTO(Long idGeneracion, Double generacionActual, Double generacionAcumulado, Double valorUnidad, Double valorTotal, Double diferenciaTarifa, Double ahorroActual, Double ahorroAcumulado, Double ahorroCodosActual, Double ahorroCodosAcumulado, Integer anio, Integer mes, Long idTarifaOperador, String idPlanta) {
        this.idGeneracion = idGeneracion;
        this.generacionActual = generacionActual;
        this.generacionAcumulado = generacionAcumulado;
        this.valorUnidad = valorUnidad;
        this.valorTotal = valorTotal;
        this.diferenciaTarifa = diferenciaTarifa;
        this.ahorroActual = ahorroActual;
        this.ahorroAcumulado = ahorroAcumulado;
        this.ahorroCodosActual = ahorroCodosActual;
        this.ahorroCodosAcumulado = ahorroCodosAcumulado;
        this.anio = anio;
        this.mes = mes;
        this.idTarifaOperador = idTarifaOperador;
        this.idPlanta = idPlanta;
    }
}
