package com.IntegracionSiesa.Controller;

import com.IntegracionSiesa.Service.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar operaciones relacionadas con clientes.
 */
@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    /**
     * Obtiene una lista de todos los clientes.
     *
     * @return ResponseEntity con la lista de clientes.
     */
    @GetMapping("/list")
    private ResponseEntity<?> listClientes() {
        return ResponseEntity.ok(clienteService.listClientes());
    }

    /**
     * Obtiene una lista de clientes especiales.
     *
     * @return ResponseEntity con la lista de clientes especiales.
     */
    @GetMapping("/list_especiales")
    private ResponseEntity<?> listClientesEspeciales() {
        return ResponseEntity.ok(clienteService.findSpecialCustomers());
    }

    /**
     * Busca un cliente por su NIT (Número de Identificación Tributaria).
     *
     * @param nit NIT del cliente que se desea buscar.
     * @return ResponseEntity con los detalles del cliente, si es encontrado.
     */
    @GetMapping("/find")
    private ResponseEntity<?> findById(@RequestParam("nit") String nit) {
        return ResponseEntity.ok(clienteService.findById(nit));
    }

}
