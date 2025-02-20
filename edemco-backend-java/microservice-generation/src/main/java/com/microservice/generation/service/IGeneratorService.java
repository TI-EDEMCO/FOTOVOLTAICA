package com.microservice.generation.service;

import com.microservice.generation.controller.sto.OperadorDto;
import com.microservice.generation.controller.sto.TarifaOperadorDto;
import com.microservice.generation.dto.DatosGeneracionDTO;
import com.microservice.generation.dto.ValorUnidadDTO;
import com.microservice.generation.dto.GeneratorDTO;
import com.microservice.generation.entities.Generator;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interfaz para definir las operaciones del servicio de generación de datos.
 */
public interface IGeneratorService {

    /**
     * Obtiene la generación acumulada de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Generación acumulada.
     */
    Double findGeneracionAcumuladaByDateAndPlanta(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el ahorro en codos acumulado de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Ahorro en codos acumulado.
     */
    Double findAhorroCodosAcumuladoByDateAndPlanta(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el ahorro acumulado de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Ahorro acumulado.
     */
    Double findAhorroAcumuladoByDateAndPlanta(Integer anio, Integer mes, String planta);

    /**
     * Procesa una lista de datos de generación y realiza cálculos relacionados.
     *
     * @param datosGeneracionDTOList Lista de datos de generación a procesar.
     * @return Respuesta indicando el estado del procesamiento.
     * @throws Exception Si ocurre un error durante el procesamiento.
     */
    ResponseEntity<?> calculate(List<DatosGeneracionDTO> datosGeneracionDTOList) throws Exception;

    /**
     * Obtiene todos los registros de generación.
     *
     * @return Lista de todos los registros de generación.
     */
    List<Generator> findAll();

    /**
     * Guarda un registro de generación en la base de datos.
     *
     * @param generator Entidad Generator a guardar.
     */
    void save(Generator generator);

    /**
     * Obtiene el último valor de unidad registrado.
     *
     * @return Objeto ValorUnidadDTO con el último valor de unidad.
     */
    ValorUnidadDTO findLastValorUnidad();

    /**
     * Obtiene una lista de datos de generación para un mes y año específicos.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @return Lista de objetos GeneratorDTO con los datos de generación.
     */
    List<GeneratorDTO> findGenerationsByDate(Integer anio, Integer mes);

    /**
     * Obtiene el ID de una planta a partir de su nombre.
     *
     * @param nombrePlanta Nombre de la planta.
     * @return ID de la planta.
     */
    String findIdPlantaByNombrePlanta(String nombrePlanta);

    /**
     * Obtiene la generación actual de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Generación actual.
     */
    Double findGeneracionActualByIdPlantaAndDate(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el ID del operador asociado a una planta.
     *
     * @param idPlanta ID de la planta.
     * @return ID del operador.
     */
    Long findIdOperadorByIdPlanta(String idPlanta);

    /**
     * Obtiene los detalles de un operador a partir de su ID.
     *
     * @param idOperador ID del operador.
     * @return Objeto OperadorDto con los detalles del operador.
     */
    OperadorDto getOperadorById(Long idOperador);

    /**
     * Obtiene la tarifa de un operador para un mes específico.
     *
     * @param idOperador ID del operador.
     * @param mes Mes para el cual se desea la tarifa.
     * @return Objeto TarifaOperadorDto con la tarifa del operador.
     */
    TarifaOperadorDto getTarifaOperadorByOperadorId(Long idOperador, Integer mes);

    /**
     * Obtiene el valor de unidad asociado a una planta específica.
     *
     * @param idPlanta ID de la planta.
     * @return Valor de unidad.
     */
    Double findValorUnidadByIdPlanta(String idPlanta);

    /**
     * Obtiene el valor total asociado a una planta para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Valor total.
     */
    Double findValorTotalByIdPlantaAndDate(Integer anio, Integer mes, String planta);

    /**
     * Verifica si una planta tiene facturación especial.
     *
     * @param idPlanta ID de la planta.
     * @return Resultado de la verificación.
     * @throws Exception Si ocurre un error en la comunicación con el cliente.
     */
    String checkFacturacionEspecial(String idPlanta) throws Exception;

    /**
     * Obtiene la cantidad de kWh de una planta específica para una fecha dada.
     *
     * @param idPlanta ID de la planta.
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @return Cantidad de kWh.
     * @throws Exception Si ocurre un error en la comunicación con el cliente.
     */
    Float findCantidadKWhByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes) throws Exception;
}
