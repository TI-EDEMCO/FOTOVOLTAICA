package com.IntegracionSiesa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaFADto {

    private int f350_id_cia;
    private String f350_id_co;
    private String f350_id_tipo_docto;
    private Integer f350_consec_docto;
    private String f284_id;
    private LocalDateTime f350_fecha_ts_creacion;

}
