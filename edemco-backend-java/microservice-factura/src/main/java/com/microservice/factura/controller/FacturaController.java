package com.microservice.factura.controller;

import com.microservice.factura.dto.FacturaRequestDTO;
import com.microservice.factura.exceptions.FacturaNotFoundException;
import com.microservice.factura.service.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historico_facturas")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    /**
     * Busca facturas por id de planta y fecha.
     *
     * @param idPlanta ID de la planta.
     * @param date     Fecha en formato String (opcional).
     * @return Lista de facturas que cumplen con los criterios.
     * @throws FacturaNotFoundException Si no se encuentran facturas.
     */
    @GetMapping("/filter")
    public ResponseEntity<?> findFacturaByNitAndDate(@RequestParam("idPlanta") String idPlanta, @RequestParam(value = "date", required = false) String date) throws FacturaNotFoundException {
        return ResponseEntity.ok(facturaService.findByIdPlantaAndDate(idPlanta, date));
    }

    /**
     * Lista facturas filtradas por el ID de una planta específica.
     *
     * @param idPlanta ID de la planta.
     * @return Lista de facturas asociadas a la planta.
     * @throws FacturaNotFoundException Si no se encuentran facturas.
     */
    @GetMapping("/planta")
    public ResponseEntity<?> listFacturaByPlanta(@RequestParam("idPlanta") String idPlanta) throws FacturaNotFoundException {
        return ResponseEntity.ok(facturaService.listFacturaByIdPlanta(idPlanta));
    }

    /**
     * Lista facturas filtradas por fecha.
     *
     * @param date Fecha en formato String.
     * @return Lista de facturas asociadas a la fecha.
     * @throws FacturaNotFoundException Si no se encuentran facturas.
     */
    @GetMapping("/date")
    public ResponseEntity<?> listFacturaByDate(@RequestParam("date") String date) throws FacturaNotFoundException {
        return ResponseEntity.ok(facturaService.listFacturaByDate(date));
    }

    /**
     * Agrega una nueva factura.
     *
     * @param facturaRequestDTO Datos de la factura a agregar.
     * @return Factura creada.
     * @throws Exception Si ocurre algún error durante el proceso.
     */
    @PostMapping()
    public ResponseEntity<?> addFactura(@RequestBody FacturaRequestDTO facturaRequestDTO) throws Exception {
        System.out.println("Número de factura recibido en el controlador: " + facturaRequestDTO.getNumeroFactura());
        return ResponseEntity.ok(facturaService.addFactura(facturaRequestDTO));
    }

}
