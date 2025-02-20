package com.IntegracionSiesa.client;

import com.IntegracionSiesa.dto.FacturaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Cliente Feign para interactuar con el microservicio de facturación.
 */
@FeignClient(name = "msvc-factura", url = "localhost:8060")
public interface FacturaClient {

    /**
     * Agrega una nueva factura al histórico de facturas en el microservicio.
     *
     * @param facturaRequestDTO Objeto que contiene los datos de la factura que se desea registrar.
     * @return ResponseEntity con la respuesta del microservicio, que puede incluir información sobre la operación realizada.
     */
    @PostMapping("/api/historico_facturas")
    ResponseEntity<?> addFactura(@RequestBody FacturaRequestDTO facturaRequestDTO);
}
