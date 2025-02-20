package com.Operadores.Controller;

import com.Operadores.Dto.TarifaOperadorDto;
import com.Operadores.Entities.Operador;
import com.Operadores.Entities.TarifaOperador;
import com.Operadores.Exceptions.ResourceNotFoundException;
import com.Operadores.Service.TarifaOperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifaoperador")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class TarifaOperadorController {

    @Autowired
    private TarifaOperadorService tarifaOperadorService;

    /**
     * Busca una tarifa específica por su ID.
     *
     * @param id ID de la tarifa a buscar.
     * @return ResponseEntity con el objeto TarifaOperador correspondiente al ID proporcionado.
     * @throws ResourceNotFoundException Si no se encuentra una tarifa con el ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarifaOperador> buscarTarifa(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(tarifaOperadorService.buscarTarifa(id).orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada para este id :: " + id)));
    }

    /**
     * Registra una lista de tarifas de operadores.
     *
     * @param tarifaOperadorList Lista de objetos TarifaOperadorDto a registrar.
     * @return Lista de objetos TarifaOperadorDto con los datos de las tarifas registradas.
     */
    @PostMapping
    public List<TarifaOperadorDto> registrarTarifa(@RequestBody List<TarifaOperadorDto> tarifaOperadorList) {
        return tarifaOperadorService.guardarTarifa(tarifaOperadorList);
    }

    /**
     * Lista todas las tarifas de operadores.
     *
     * @return ResponseEntity con una lista de todas las tarifas en formato TarifaOperadorDto.
     */
    @GetMapping("/all")
    public ResponseEntity<?> listTarifaOperador() {
        return ResponseEntity.ok(tarifaOperadorService.lisTarifaOperador());
    }

    /**
     * Encuentra el operador asociado a una tarifa específica.
     *
     * @param tarifaOperadorDto Objeto TarifaOperadorDto con el ID del operador a buscar.
     * @return Objeto Operador correspondiente al ID proporcionado en el DTO.
     */
    @GetMapping("/operador")
    public Operador encontrarOperador(@RequestBody TarifaOperadorDto tarifaOperadorDto) {
        return tarifaOperadorService.encontrarOperador(tarifaOperadorDto);
    }

    /**
     * Lista las últimas tarifas registradas para cada operador.
     *
     * @return ResponseEntity con una lista de las últimas tarifas en formato TarifaOperadorDto.
     */
    @GetMapping("/last_tarifas")
    public ResponseEntity<?> findLastTarifaOperadores() {
        return ResponseEntity.ok(tarifaOperadorService.findLastTarifaOperadores());
    }

    /**
     * Busca una tarifa específica de un operador para un mes dado.
     *
     * @param idOperador ID del operador cuya tarifa se desea buscar.
     * @param mes Mes para el cual se desea buscar la tarifa.
     * @return Objeto TarifaOperadorDto con los datos de la tarifa encontrada.
     */
    @GetMapping("/tarifaoperadordto")
    public TarifaOperadorDto findTarifaOperadorByIdOperadorAndMonth(@RequestParam(name = "idOperador") Long idOperador,
                                                                    @RequestParam(name = "mes") Integer mes) {
        return tarifaOperadorService.findTarifaOperadorByIdOperadorAndMonth(idOperador, mes);
    }
}
