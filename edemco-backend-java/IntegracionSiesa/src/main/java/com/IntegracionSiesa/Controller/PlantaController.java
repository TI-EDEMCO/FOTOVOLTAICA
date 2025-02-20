package com.IntegracionSiesa.Controller;

import com.IntegracionSiesa.Service.PlantaServiceImpl;
import com.IntegracionSiesa.dto.PlantaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con plantas.
 */
@RestController
@RequestMapping("/api/planta")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class PlantaController {

    @Autowired
    private PlantaServiceImpl plantaService;

    /**
     * Obtiene una lista de todas las plantas registradas.
     *
     * @return ResponseEntity con la lista de plantas.
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAllPlantas() {
        return ResponseEntity.ok(plantaService.findAllPlantas());
    }

    /**
     * Obtiene el identificador de una planta a partir de su nombre.
     *
     * @param nombrePlanta Nombre de la planta que se desea buscar.
     * @return El identificador de la planta como un String.
     */
    @GetMapping("/idplanta")
    public String findIdPlantaByNombrePlanta(@RequestParam("nombrePlanta") String nombrePlanta) {
        return plantaService.findIdPlantaByNombrePlanta(nombrePlanta);
    }

    /**
     * Obtiene el identificador del operador asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return El identificador del operador como un Long.
     */
    @GetMapping("/idoperador")
    public Long findIdOperadorByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta) {
        return plantaService.findIdOperadorByIdPlanta(idPlanta);
    }

    /**
     * Obtiene el valor de la unidad asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return El valor de la unidad como un Double.
     */
    @GetMapping("/valorUnidad")
    public Double findValorUnidadByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta) {
        return plantaService.findValorUnidadByIdPlanta(idPlanta);
    }

    /**
     * Verifica si una planta está registrada en facturación especial.
     *
     * @param idPlanta Identificador de la planta.
     * @return Un String indicando si la planta está registrada en facturación especial.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    @GetMapping("/checkfacturacionespecial")
    public String checkFacturacionEspecial(@RequestParam(name = "idPlanta") String idPlanta) throws Exception {
        return plantaService.verifyPlantaInFacturacionEspecial(idPlanta);
    }

    /**
     * Actualiza los detalles de una lista de plantas.
     *
     * @param plantaDtoList Lista de objetos PlantaDto con los nuevos detalles de las plantas.
     * @return La lista actualizada de plantas.
     */
    @PatchMapping("/updatePlanta")
    public List<PlantaDto> updatePlanta(@RequestBody List<PlantaDto> plantaDtoList) {
        return plantaService.modifyPlanta(plantaDtoList);
    }
}
