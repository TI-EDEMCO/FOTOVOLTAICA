package com.IntegracionSiesa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDto {

    private Long idCliente;
    private String nombreCliente;
    private String contrato;
    private String direccion;
    private Integer nit;
    private String correo;
    private Long idOperador;
    private Long idTipoCliente;

    public ClienteDto(Long idCliente, String nombreCliente, String contrato, String direccion, Integer nit, String correo, Long idOperador, Long idTipoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.contrato = contrato;
        this.direccion = direccion;
        this.nit = nit;
        this.correo = correo;
        this.idOperador = idOperador;
        this.idTipoCliente = idTipoCliente;
    }

}
