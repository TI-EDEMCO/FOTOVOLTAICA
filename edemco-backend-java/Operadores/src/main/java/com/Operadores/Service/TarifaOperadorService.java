package com.Operadores.Service;

import com.Operadores.Dto.TarifaOperadorDto;
import com.Operadores.Entities.Operador;
import com.Operadores.Entities.TarifaOperador;
import com.Operadores.Exceptions.ResourceNotFoundException;
import com.Operadores.Repository.OperadorRepository;
import com.Operadores.Repository.TarifaOperadorRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TarifaOperadorService {

    @Autowired
    private TarifaOperadorRepository tarifaOperadorRepository;

    @Autowired
    private OperadorRepository operadorRepository;

    Logger LOGGER = Logger.getLogger(TarifaOperadorService.class);

    /**
     * Guarda una lista de tarifas de operadores.
     *
     * @param dto Lista de objetos TarifaOperadorDto a guardar.
     * @return Lista de objetos TarifaOperadorDto con los datos actualizados tras guardarlos.
     */
    public List<TarifaOperadorDto> guardarTarifa(List<TarifaOperadorDto> dto) {
        LOGGER.info("Se ha guardado un registro");

        LocalDate currentDate = LocalDate.now();
        int mes = currentDate.getMonthValue() == 1 ? 12 : currentDate.getMonthValue() - 1;
        int anio = currentDate.getMonthValue() == 1 ? currentDate.getYear() - 1 : currentDate.getYear();

        List<TarifaOperadorDto> newTarifaOperador = new ArrayList<>();

        for (TarifaOperadorDto tarifaOperadorDto : dto) {
            Operador operador = operadorRepository.findOperadorById(tarifaOperadorDto.getIdOperador());
            tarifaOperadorRepository.save(TarifaOperador.builder()
                    .idTarifaOperador(tarifaOperadorDto.getIdTarifa())
                    .tarifaOperador(tarifaOperadorDto.getTarifaOperador())
                    .anio(anio)
                    .mes(mes)
                    .operador(operador).build());

            TarifaOperadorDto nuevaTarifaOperadorDto = new TarifaOperadorDto();
            nuevaTarifaOperadorDto.setIdTarifa(tarifaOperadorDto.getIdTarifa());
            nuevaTarifaOperadorDto.setMes(mes);
            nuevaTarifaOperadorDto.setAnio(anio);
            nuevaTarifaOperadorDto.setIdOperador(tarifaOperadorDto.getIdOperador());
            nuevaTarifaOperadorDto.setTarifaOperador(tarifaOperadorDto.getTarifaOperador());

            newTarifaOperador.add(nuevaTarifaOperadorDto);
        }

        return newTarifaOperador;
    }

    /**
     * Busca una tarifa por su ID.
     *
     * @param id ID de la tarifa a buscar.
     * @return Objeto Optional que contiene la tarifa encontrada.
     * @throws ResourceNotFoundException Si no se encuentra una tarifa con el ID proporcionado.
     */
    public Optional<TarifaOperador> buscarTarifa(Long id) throws ResourceNotFoundException {
        LOGGER.info("Iniciando la búsqueda de una tarifa con id: " + id);
        Optional<TarifaOperador> tarifaABuscar = tarifaOperadorRepository.findById(id);
        if (tarifaABuscar.isPresent()) {
            return tarifaABuscar;
        } else {
            throw new ResourceNotFoundException("El registro con id: " + id + " no existe.");
        }
    }

    /**
     * Obtiene una lista de todas las tarifas de operadores.
     *
     * @return Lista de objetos TarifaOperadorDto con todas las tarifas de operadores.
     */
    public List<TarifaOperadorDto> lisTarifaOperador() {
        return tarifaOperadorRepository.findAllPlantas();
    }

    /**
     * Obtiene una lista de las últimas tarifas registradas de cada operador.
     *
     * @return Objeto Optional que contiene una lista de TarifaOperadorDto con las últimas tarifas.
     */
    public Optional<List<TarifaOperadorDto>> findLastTarifaOperadores() {
        return Optional.ofNullable(tarifaOperadorRepository.findLastTarifasOperadores());
    }

    /**
     * Encuentra un operador asociado a una tarifa.
     *
     * @param dto Objeto TarifaOperadorDto que contiene el ID del operador a buscar.
     * @return Objeto Operador correspondiente al ID proporcionado en el DTO.
     */
    public Operador encontrarOperador(TarifaOperadorDto dto) {
        return operadorRepository.findOperadorById(dto.getIdOperador());
    }

    /**
     * Busca una tarifa específica de un operador para un mes dado.
     *
     * @param idOperador ID del operador cuya tarifa se desea buscar.
     * @param mes Mes para el cual se desea buscar la tarifa.
     * @return Objeto TarifaOperadorDto con los datos de la tarifa encontrada.
     */
    public TarifaOperadorDto findTarifaOperadorByIdOperadorAndMonth(Long idOperador, Integer mes) {
        return tarifaOperadorRepository.findTarifaOperadorByIdOperadorAndMonth(idOperador, mes);
    }
}
