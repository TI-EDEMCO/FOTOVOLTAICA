package com.microservice.facturacion_especial.service;

import com.microservice.facturacion_especial.dto.FacturacionEspecialDTO;
import com.microservice.facturacion_especial.entities.FacturacionEspecial;
import com.microservice.facturacion_especial.exceptions.FacturacionEspecialException;

import java.util.List;

/**
 * Interfaz que define los métodos del servicio para gestionar la facturación especial.
 */
public interface IFacturacionEspecialService {

    /**
     * Recupera todas las entidades de facturación especial.
     *
     * @return Lista de entidades {@link FacturacionEspecial}.
     */
    List<FacturacionEspecial> findAll();

    /**
     * Busca una facturación especial por su identificador.
     *
     * @param id Identificador único de la facturación especial.
     * @return La entidad encontrada.
     */
    FacturacionEspecial findById(Long id);

    /**
     * Recupera el último valor de exportación asociado a un ID de planta.
     *
     * @param idPlanta Identificador de la planta.
     * @return El último valor de exportación.
     * @throws Exception Si no se encuentra el valor o si ocurre un error.
     */
    Double findLastValorExportacionByIdPlanta(String idPlanta) throws Exception;

    /**
     * Guarda una nueva facturación especial en la base de datos.
     *
     * @param facturacionEspecial DTO con los datos necesarios para crear la facturación especial.
     * @return La entidad de facturación especial guardada.
     * @throws FacturacionEspecialException Si los datos proporcionados no son válidos.
     */
    FacturacionEspecial save(FacturacionEspecialDTO facturacionEspecial) throws FacturacionEspecialException;

    /**
     * Recupera la cantidad de kWh exportados por una planta en un año y mes específicos.
     *
     * @param idPlanta Identificador de la planta.
     * @param anio Año de la consulta.
     * @param mes Mes de la consulta.
     * @return La cantidad de kWh exportados.
     * @throws Exception Si ocurre algún error al recuperar los datos.
     */
    Float findCantidadKwhByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes) throws Exception;
}
