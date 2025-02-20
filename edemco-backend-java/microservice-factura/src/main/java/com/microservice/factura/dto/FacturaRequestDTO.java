package com.microservice.factura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturaRequestDTO {

    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    private Integer diasFacturados;
    private String numeroFactura;
    private String idPlanta;
    private Integer idCliente;



}
