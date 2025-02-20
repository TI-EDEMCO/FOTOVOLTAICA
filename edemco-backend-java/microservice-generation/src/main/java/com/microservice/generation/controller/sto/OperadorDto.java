package com.microservice.generation.controller.sto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperadorDto {

    private Long idOperador;
    private String nombreOperador;
    private String imgLogo;
}
