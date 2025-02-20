package com.IntegracionSiesa.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "F350_ID_CO", "F350_ID_TIPO_DOCTO", "F350_CONSEC_DOCTO", "F350_FECHA",
        "F350_ID_TERCERO", "F350_NOTAS", "F311_ID_SUCURSAL_CLI",
        "F311_ID_TIPO_CLI", "F311_ID_COND_PAGO"
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoVentaServicio {

    @JsonProperty("F350_ID_CO")
    private String F350_ID_CO;

    @JsonProperty("F350_ID_TIPO_DOCTO")
    private String F350_ID_TIPO_DOCTO;

    @JsonProperty("F350_CONSEC_DOCTO")
    private String F350_CONSEC_DOCTO;

    @JsonProperty("F350_FECHA")
    private String F350_FECHA;

    @JsonProperty("F350_ID_TERCERO")
    private String F350_ID_TERCERO;

    @JsonProperty("F350_NOTAS")
    private String F350_NOTAS;

    @JsonProperty("F311_ID_SUCURSAL_CLI")
    private String F311_ID_SUCURSAL_CLI;

    @JsonProperty("F311_ID_TIPO_CLI")
    private String F311_ID_TIPO_CLI;

    @JsonProperty("F311_ID_COND_PAGO")
    private String F311_ID_COND_PAGO;

}
