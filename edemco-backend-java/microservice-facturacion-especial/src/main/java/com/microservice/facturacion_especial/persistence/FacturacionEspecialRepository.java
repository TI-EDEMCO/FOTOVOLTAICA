package com.microservice.facturacion_especial.persistence;

import com.microservice.facturacion_especial.entities.FacturacionEspecial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repositorio para realizar operaciones CRUD y consultas personalizadas sobre la entidad {@link FacturacionEspecial}.
 */
public interface FacturacionEspecialRepository extends CrudRepository<FacturacionEspecial, Long> {

    /**
     * Obtiene el último valor de exportación asociado a un ID de planta.
     *
     * @param idPlanta Identificador de la planta.
     * @return {@link Optional} que contiene el valor de exportación si existe.
     */
    @Query("SELECT f.valorExportacion FROM FacturacionEspecial f WHERE f.idPlanta = :idPlanta ORDER BY f.idFacturacionEspecial DESC LIMIT 1")
    Optional<Double> findLastValorExportacionByIdPlanta(String idPlanta);

    /**
     * Obtiene la cantidad de kWh exportados por una planta en un año y mes específicos.
     *
     * @param idPlanta Identificador de la planta.
     * @param anio Año de la consulta.
     * @param mes Mes de la consulta.
     * @return Cantidad de kWh exportados.
     */
    @Query("SELECT f.cantidadkWh FROM FacturacionEspecial f WHERE f.idPlanta = :idPlanta AND f.anio = :anio AND f.mes = :mes ORDER BY f.idFacturacionEspecial DESC LIMIT 1")
    Float findCantidadKwhByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes);
}
