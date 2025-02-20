package com.microservice.generation.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.generation.dto.GeneratorDTO;
import com.microservice.generation.dto.ValorUnidadDTO;
import com.microservice.generation.entities.Generator;

/**
 * Repositorio para acceder a los datos de la entidad Generator.
 */
@Repository
public interface GeneratorRepository extends CrudRepository<Generator, Long> {

    /**
     * Obtiene la generación acumulada de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Generación acumulada.
     */
    @Query("SELECT g.generacionAcumulado FROM Generator g WHERE g.anio = :anio AND g.mes = :mes AND g.idPlanta = :planta")
    Double findGeneracionAcumuladaByDateAndPlanta(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el ahorro en codos acumulado de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Ahorro en codos acumulado.
     */
    @Query("SELECT g.ahorroCodosAcumulado FROM Generator g WHERE g.anio = :anio AND g.mes = :mes AND g.idPlanta = :planta")
    Double findAhorroCodosAcumuladoByDateAndPlanta(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el ahorro acumulado de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Ahorro acumulado.
     */
    @Query("SELECT g.ahorroAcumulado FROM Generator g WHERE g.anio = :anio AND g.mes = :mes AND g.idPlanta = :planta")
    Double findAhorroAcumuladoByDateAndPlanta(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el último valor de unidad registrado.
     *
     * @return Objeto ValorUnidadDTO con los datos del último valor de unidad.
     */
    @Query("SELECT new com.microservice.generation.dto.ValorUnidadDTO (g.anio, g.mes, g.valorUnidad) FROM Generator g ORDER BY g.idGeneracion DESC LIMIT 1")
    ValorUnidadDTO findLastValorUnidad();

    /**
     * Obtiene una lista de datos de generación para una planta específica en una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Lista de objetos GeneratorDTO con los datos de generación.
     */
    @Query("SELECT new com.microservice.generation.dto.GeneratorDTO(g.idGeneracion, g.generacionActual, g.generacionAcumulado, g.valorUnidad, " +
            "g.valorTotal, g.diferenciaTarifa, g.ahorroActual, g.ahorroAcumulado, g.ahorroCodosActual, g.ahorroCodosAcumulado, g.anio, g.mes, " +
            "g.idTarifaOperador, g.idPlanta) FROM Generator g WHERE g.mes = :mes AND g.anio = :anio AND g.idPlanta = :planta")
    List<GeneratorDTO> findGenerationsByDate(Integer anio, Integer mes, String planta);

    /**
     * Obtiene la generación actual de una planta específica para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Generación actual.
     */
    @Query("SELECT g.generacionActual FROM Generator g WHERE g.anio = :anio AND g.mes = :mes AND g.idPlanta = :planta")
    Double findGeneracionActualByIdPlantaAndDate(Integer anio, Integer mes, String planta);

    /**
     * Obtiene el valor total asociado a la generación de una planta para una fecha dada.
     *
     * @param anio Año de la fecha.
     * @param mes Mes de la fecha.
     * @param planta ID de la planta.
     * @return Valor total de la generación.
     */
    @Query("SELECT g.valorTotal FROM Generator g WHERE g.anio = :anio AND g.mes = :mes AND g.idPlanta = :planta")
    Double findValorTotalByIdPlantaAndDate(Integer anio, Integer mes, String planta);
}
