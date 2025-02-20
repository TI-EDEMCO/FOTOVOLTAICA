package com.Operadores.Controller;

import com.Operadores.Dto.OperadorDto;
import com.Operadores.Service.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operador")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    /**
     * Método para listar todos los operadores.
     *
     * @return ResponseEntity que contiene una lista de objetos OperadorDto.
     */
    @GetMapping
    public ResponseEntity<List<OperadorDto>> listarOperadores() {
        return ResponseEntity.ok(operadorService.listarOperadores());
    }

    /**
     * Método para obtener un operador específico por su ID.
     *
     * @param idOperador ID del operador que se desea buscar.
     * @return Objeto OperadorDto correspondiente al operador solicitado.
     */
    @GetMapping("/operadordto")
    public OperadorDto getOperadorById(@RequestParam("idOperador") Long idOperador) {
        return operadorService.findOperadorDtoById(idOperador);
    }
}
