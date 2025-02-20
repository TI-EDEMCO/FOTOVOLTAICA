package com.microservice.generation.controller;

import com.microservice.generation.dto.DatosGeneracionDTO;
import com.microservice.generation.dto.ValorUnidadDTO;
import com.microservice.generation.service.GeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de generación de datos y cálculos relacionados.
 */
@RestController
@RequestMapping("/api/generacion")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class GeneratorController {

    @Autowired
    private GeneratorServiceImpl generatorService;

    /**
     * Realiza cálculos de generación basados en una lista de datos proporcionada.
     *
     * @param datosGeneracionDTOList Lista de objetos DatosGeneracionDTO con la información necesaria para los cálculos.
     * @return Respuesta con los resultados de los cálculos.
     * @throws Exception Si ocurre un error durante los cálculos.
     */
    @PostMapping("/calculos")
    public ResponseEntity<?> calculosGeneracion(@RequestBody List<DatosGeneracionDTO> datosGeneracionDTOList) throws Exception {
        return ResponseEntity.ok(generatorService.calculate(datosGeneracionDTOList));
    }

    /**
     * Obtiene el último valor de unidad registrado.
     *
     * @return Objeto ValorUnidadDTO con la información del último valor de unidad.
     */
    @GetMapping("/valor_unidad")
    public ValorUnidadDTO findLastValorUnidad() {
        return generatorService.findLastValorUnidad();
    }

    /**
     * Obtiene la generación actual de una planta específica para una fecha dada.
     *
     * @param idPlanta ID de la planta.
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @return Generación actual en kilovatios hora (kWh) para la planta y fecha especificada.
     */
    @GetMapping("/generacion_actual")
    public Double findGeneracionActualByIdPlantaAndDate(@RequestParam(name = "idPlanta") String idPlanta,
                                                        @RequestParam(name = "anio") Integer anio,
                                                        @RequestParam(name = "mes") Integer mes) {
        return generatorService.findGeneracionActualByIdPlantaAndDate(anio, mes, idPlanta);
    }

    /**
     * Obtiene el valor total asociado a la generación de una planta para una fecha dada.
     *
     * @param idPlanta ID de la planta.
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @return Valor total asociado a la generación para la planta y fecha especificada.
     */
    @GetMapping("/valor_total")
    public Double findValorTotalByIdPlantaAndDate(@RequestParam(name = "idPlanta") String idPlanta,
                                                  @RequestParam(name = "anio") Integer anio,
                                                  @RequestParam(name = "mes") Integer mes) {
        return generatorService.findValorTotalByIdPlantaAndDate(anio, mes, idPlanta);
    }
}
