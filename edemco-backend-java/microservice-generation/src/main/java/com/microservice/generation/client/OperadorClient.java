package com.microservice.generation.client;

import com.microservice.generation.controller.sto.OperadorDto;
import com.microservice.generation.controller.sto.TarifaOperadorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para interactuar con el microservicio de operadores.
 */
@FeignClient(name = "msvc-operadores", url = "localhost:9091")
public interface OperadorClient {

    /**
     * Obtiene los detalles de un operador a partir de su ID.
     *
     * @param idOperador ID del operador para obtener los detalles.
     * @return Objeto OperadorDto con la información del operador.
     */
    @GetMapping("/api/operador/operadordto")
    OperadorDto getOperadorById(@RequestParam("idOperador") Long idOperador);

    /**
     * Obtiene la tarifa de un operador para un mes específico.
     *
     * @param idOperador ID del operador para buscar la tarifa.
     * @param mes Mes para el cual se desea obtener la tarifa.
     * @return Objeto TarifaOperadorDto con la información de la tarifa del operador.
     */
    @GetMapping("/api/tarifaoperador/tarifaoperadordto")
    TarifaOperadorDto findTarifaOperadorByIdOperadorAndMonth(@RequestParam(name = "idOperador") Long idOperador,
                                                             @RequestParam(name = "mes") Integer mes);

}
