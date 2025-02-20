package com.microservice.generation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para interactuar con el microservicio de facturación especial.
 */
@FeignClient(name = "msvc-facturacion-especial", url = "localhost:9081")
public interface FacturacionEspecialClient {

    /**
     * Obtiene la cantidad de kilovatios hora (kWh) para una planta específica en una fecha determinada.
     *
     * @param idPlanta ID de la planta para la cual se desea obtener el consumo de kWh.
     * @param anio Año del periodo de consumo.
     * @param mes Mes del periodo de consumo.
     * @return Cantidad de kWh consumidos en el periodo especificado.
     * @throws Exception Si ocurre un error durante la comunicación con el microservicio.
     */
    @GetMapping("/api/facturacion_especial/cantidadkwh")
    Float findCantidadKWhByIdPlantaAndDate(@RequestParam(name = "idPlanta") String idPlanta,
                                           @RequestParam(name = "anio") Integer anio,
                                           @RequestParam(name = "mes") Integer mes) throws Exception;
    /**
     * Obtiene el último valor de exportación asociado a una planta.
     */
    @GetMapping("/api/facturacion_especial/valor_exportacion")
    Double findLastValorExportacionByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta);
}
