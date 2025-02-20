package com.IntegracionSiesa.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentasServicios {

    @JsonProperty("Docto. ventas servicios")
    private List<DocumentoVentaServicio> doctoVentasServicios;
    @JsonProperty("Movto. ventas servicios")
    private List<MovimientoVentaServicio> movtoVentasServicios;
}
