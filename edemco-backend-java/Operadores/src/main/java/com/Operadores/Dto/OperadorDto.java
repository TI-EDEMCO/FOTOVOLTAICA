package com.Operadores.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperadorDto {

    private Long idOperador;
    private String nombreOperador;
    private String imgLogo;

    public OperadorDto(Long idOperador, String nombreOperador, String imgLogo) {
        this.idOperador = idOperador;
        this.nombreOperador = nombreOperador;
        this.imgLogo = imgLogo;
    }
}
