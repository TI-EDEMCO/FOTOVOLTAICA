package com.IntegracionSiesa.Controller;

import com.IntegracionSiesa.Service.SiesaPruebasService;
import com.IntegracionSiesa.Service.SiesaService;
import com.IntegracionSiesa.dto.IniciarFacturacionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/integracion_siesa")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class SiesaController {

    @Autowired
    private SiesaService service;

    @Autowired
    private SiesaPruebasService siesaPruebasService;

    /**
     * Llena datos necesarios para la facturación basada en una lista de centros de operación y una fecha específica.
     *
     * @param centroOperacionList Lista de objetos IniciarFacturacionDto que representan los centros de operación.
     * @param date Fecha para la cual se deben llenar los datos.
     * @return ResponseEntity con la respuesta de la operación.
     */
    @GetMapping("/llenar_datos")
    public ResponseEntity<?> llenarDatos(@RequestBody List<IniciarFacturacionDto> centroOperacionList, @RequestParam("date") LocalDate date){
        return service.llenarDatos2(centroOperacionList,date);
    }

    /**
     * Envía facturas a Siesa basándose en una lista de facturaciones y una fecha específica.
     *
     * @param iniciarFacturacionDtoList Lista de objetos IniciarFacturacionDto que representan las facturaciones a enviar.
     * @param date Fecha para la cual se deben enviar las facturas.
     * @return ResponseEntity con el estado de la operación.
     */
    @PostMapping("/enviar_factura_siesa")
    public ResponseEntity<?> enviarFacturaSiesa(@RequestBody List<IniciarFacturacionDto> iniciarFacturacionDtoList, @RequestParam("date") LocalDate date) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service
                    .envioFacturas(iniciarFacturacionDtoList, date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
