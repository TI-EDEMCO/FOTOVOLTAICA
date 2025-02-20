package com.IntegracionSiesa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para interactuar con el microservicio de generación.
 */
@FeignClient(name = "msvc-generation", url = "localhost:9092")
public interface GeneracionClient {

    /**
     * Obtiene la generación actual de una planta específica en un mes y año determinados.
     *
     * @param idPlanta Identificador único de la planta.
     * @param anio Año para el cual se desea consultar la generación.
     * @param mes Mes para el cual se desea consultar la generación.
     * @return La generación actual como un valor numérico.
     */
    @GetMapping("/api/generacion/generacion_actual")
    Double findGeneracionActualByIdPlantaAndDate(@RequestParam(name = "idPlanta") String idPlanta,
                                                 @RequestParam(name = "anio") Integer anio,
                                                 @RequestParam(name = "mes") Integer mes);

    /**
     * Obtiene el valor total asociado a una planta específica en un mes y año determinados.
     *
     * @param idPlanta Identificador único de la planta.
     * @param anio Año para el cual se desea consultar el valor total.
     * @param mes Mes para el cual se desea consultar el valor total.
     * @return El valor total como un valor numérico.
     */
    @GetMapping("/api/generacion/valor_total")
    Double findValorTotalByIdPlantaAndDate(@RequestParam(name = "idPlanta") String idPlanta,
                                           @RequestParam(name = "anio") Integer anio,
                                           @RequestParam(name = "mes") Integer mes);
}
