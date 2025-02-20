package com.microservice.generation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para interactuar con el microservicio de integración Siesa.
 */
@FeignClient(name = "msvc-integracion", url = "localhost:9090")
public interface IntegracionSiesaClient {

    /**
     * Obtiene el ID de una planta a partir de su nombre.
     *
     * @param nombrePlanta Nombre de la planta para buscar su ID.
     * @return ID de la planta.
     */
    @GetMapping("/api/planta/idplanta")
    String findIdPlantaByNombrePlanta(@RequestParam("nombrePlanta") String nombrePlanta);

    /**
     * Obtiene el ID del operador asociado a una planta específica.
     *
     * @param idPlanta ID de la planta para buscar el operador.
     * @return ID del operador asociado.
     */
    @GetMapping("/api/planta/idoperador")
    Long findIdOperadorByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta);

    /**
     * Obtiene el valor de la unidad asociada a una planta específica.
     *
     * @param idPlanta ID de la planta para obtener el valor de la unidad.
     * @return Valor de la unidad.
     */
    @GetMapping("/api/planta/valorUnidad")
    Double findValorUnidadByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta);

    /**
     * Verifica si una planta específica tiene facturación especial habilitada.
     *
     * @param idPlanta ID de la planta para verificar la facturación especial.
     * @return Estado de facturación especial como texto.
     * @throws Exception Si ocurre un error durante la comunicación con el microservicio.
     */
    @GetMapping("/api/planta/checkfacturacionespecial")
    String checkFacturacionEspecial(@RequestParam(name = "idPlanta") String idPlanta) throws Exception;

}
