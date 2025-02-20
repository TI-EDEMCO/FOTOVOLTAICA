package com.IntegracionSiesa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para interactuar con el microservicio de facturación especial.
 */
@FeignClient(name = "msvc-facturacion-especial", url = "localhost:9081")
public interface FacturacionEspecialClient {

    /**
     * Obtiene el último valor de exportación asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta para la cual se desea consultar el valor de exportación.
     * @return ResponseEntity con la respuesta del microservicio, que puede incluir el valor de exportación u otros datos relacionados.
     */
    @GetMapping("/api/facturacion_especial/valor_exportacion")
    ResponseEntity<?> findLastValorExportacionByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta);
}
