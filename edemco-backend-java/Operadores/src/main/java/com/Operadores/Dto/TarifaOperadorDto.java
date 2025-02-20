package com.Operadores.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TarifaOperadorDto {

    private Long idTarifa;
    private Double tarifaOperador;
    private Integer mes;
    private Integer anio;
    private Long idOperador;

    public TarifaOperadorDto(Long idTarifa, Double tarifaOperador, Integer mes, Integer anio, Long idOperador) {
        this.idTarifa = idTarifa;
        this.tarifaOperador = tarifaOperador;
        this.mes = mes;
        this.anio = anio;
        this.idOperador = idOperador;
    }

    public Long getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Long idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Double getTarifaOperador() {
        return tarifaOperador;
    }

    public void setTarifaOperador(Double tarifaOperador) {
        this.tarifaOperador = tarifaOperador;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Long id_operador) {
        this.idOperador = id_operador;
    }
}