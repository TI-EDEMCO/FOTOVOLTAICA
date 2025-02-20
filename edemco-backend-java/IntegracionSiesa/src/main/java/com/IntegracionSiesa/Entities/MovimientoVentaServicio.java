package com.IntegracionSiesa.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "F350_ID_CO", "F350_ID_TIPO_DOCTO", "F350_CONSEC_DOCTO", "F320_ROWID",
        "F320_ID_SERVICIO", "F320_ID_CO_MOVTO", "F320_ID_UN_MOVTO",
        "F320_ID_CCOSTO_MOVTO", "F320_ID_TERCERO_MOVTO",
        "F320_ID_SUCURSAL_CLIENTE", "F320_CANTIDAD", "F320_VLR_BRUTO", "F320_NOTAS"
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoVentaServicio {
    @JsonProperty("F350_ID_CO")
    private String F350_ID_CO;

    @JsonProperty("F350_ID_TIPO_DOCTO")
    private String F350_ID_TIPO_DOCTO;

    @JsonProperty("F350_CONSEC_DOCTO")
    private String F350_CONSEC_DOCTO;

    @JsonProperty("F320_ROWID")
    private String F320_ROWID;

    @JsonProperty("F320_ID_SERVICIO")
    private String F320_ID_SERVICIO;

    @JsonProperty("F320_ID_CO_MOVTO")
    private String F320_ID_CO_MOVTO;

    @JsonProperty("F320_ID_UN_MOVTO")
    private String F320_ID_UN_MOVTO;

    @JsonProperty("F320_ID_CCOSTO_MOVTO")
    private String F320_ID_CCOSTO_MOVTO;

    @JsonProperty("F320_ID_TERCERO_MOVTO")
    private String F320_ID_TERCERO_MOVTO;

    @JsonProperty("F320_ID_SUCURSAL_CLIENTE")
    private String F320_ID_SUCURSAL_CLIENTE;

    @JsonProperty("F320_CANTIDAD")
    private String F320_CANTIDAD;

    @JsonProperty("F320_VLR_BRUTO")
    private String F320_VLR_BRUTO;

    @JsonProperty("F320_NOTAS")
    private String F320_NOTAS;
}
