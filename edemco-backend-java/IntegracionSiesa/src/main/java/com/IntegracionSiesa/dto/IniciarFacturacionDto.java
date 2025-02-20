package com.IntegracionSiesa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IniciarFacturacionDto {

    private String idPlanta;
    private String nombrePlanta;

}
