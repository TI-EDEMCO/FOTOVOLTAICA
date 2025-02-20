package com.microservice.facturacion_especial.controller;

import com.microservice.facturacion_especial.dto.FacturacionEspecialDTO;
import com.microservice.facturacion_especial.entities.FacturacionEspecial;
import com.microservice.facturacion_especial.exceptions.FacturacionEspecialException;
import com.microservice.facturacion_especial.service.IFacturacionEspecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/facturacion_especial")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class FacturacionEspecialController {

    @Autowired
    private IFacturacionEspecialService iFacturacionEspecialService;

    /**
     * Obtiene la lista de todas las facturas especiales.
     *
     * @return ResponseEntity con la lista de facturas especiales.
     */
    @GetMapping("/all")
    public ResponseEntity<?> listFacturasEspeciales() {
        return ResponseEntity.ok(iFacturacionEspecialService.findAll());
    }

    /**
     * Crea una nueva facturación especial.
     *
     * @param facturacionEspecialDTO Datos de la facturación especial.
     * @return ResponseEntity con los datos de la facturación creada y un mensaje de éxito.
     * @throws FacturacionEspecialException Si ocurre algún error en la creación.
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createFacturacionEspecial(@RequestBody FacturacionEspecialDTO facturacionEspecialDTO) throws FacturacionEspecialException {
        FacturacionEspecial data = iFacturacionEspecialService.save(facturacionEspecialDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Facturación especial generada correctamente");
        response.put("status", "success");
        response.put("statusCode", 200);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene el último valor de exportación asociado a un ID de planta.
     *
     * @param idPlanta Identificador de la planta.
     * @return ResponseEntity con el valor de exportación o un mensaje de error si no se encuentra.
     */
    @GetMapping("/valor_exportacion")
    public ResponseEntity<?> findLastValorExportacionByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iFacturacionEspecialService.findLastValorExportacionByIdPlanta(idPlanta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Calcula la cantidad de kWh exportados por una planta en una fecha específica.
     *
     * @param idPlanta Identificador de la planta.
     * @param anio Año de la consulta.
     * @param mes Mes de la consulta.
     * @return Cantidad de kWh exportados.
     * @throws Exception Si ocurre algún error en la consulta.
     */
    @GetMapping("/cantidadkwh")
    public Float findCantidadKWhByIdPlantaAndDate(@RequestParam(name = "idPlanta") String idPlanta,
                                                  @RequestParam(name = "anio") Integer anio,
                                                  @RequestParam(name = "mes") Integer mes) throws Exception {
        return iFacturacionEspecialService.findCantidadKwhByIdPlantaAndDate(idPlanta, anio, mes);
    }
}
