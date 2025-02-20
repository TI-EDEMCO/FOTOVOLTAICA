package com.microservice.factura.persistence;

import com.microservice.factura.entities.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Factura, encargado de las operaciones con la base de datos.
 */
@Repository
public interface FacturaRepository extends CrudRepository<Factura, Long> {

    /**
     * Obtiene una lista de facturas asociadas a un ID de planta específico.
     *
     * @param idPlanta ID de la planta.
     * @return Lista de facturas relacionadas con la planta.
     */
    @Query("SELECT f FROM Factura f WHERE f.idPlanta = :idPlanta")
    List<Factura> listfacturaByIdPlanta(String idPlanta);

    /**
     * Obtiene una lista de facturas asociadas a un ID de planta y filtradas por año y mes.
     *
     * @param idPlanta ID de la planta.
     * @param year     Año de la fecha.
     * @param month    Mes de la fecha.
     * @return Lista de facturas relacionadas con la planta y el periodo de tiempo.
     */
    @Query("SELECT f FROM Factura f WHERE f.idPlanta = :idPlanta AND EXTRACT(YEAR FROM f.fechaInicial) = :year AND EXTRACT(MONTH FROM f.fechaInicial) = :month")
    List<Factura> listFacturaByIdPlantaAndDate(String idPlanta, String year, String month);

    /**
     * Obtiene una lista de facturas filtradas por año y mes.
     *
     * @param year  Año de la fecha.
     * @param month Mes de la fecha.
     * @return Lista de facturas asociadas al periodo de tiempo.
     */
    @Query("SELECT f FROM Factura f WHERE EXTRACT(YEAR FROM f.fechaInicial) = :year AND EXTRACT(MONTH FROM f.fechaInicial) = :month")
    List<Factura> listFacturaByDate(String year, String month);
}
