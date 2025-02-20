package com.microservice.factura.service;

import com.microservice.factura.dto.FacturaDTO;
import com.microservice.factura.dto.FacturaRequestDTO;
import com.microservice.factura.entities.Factura;
import com.microservice.factura.exceptions.FacturaNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para definir los servicios relacionados con facturas.
 */
public interface IFacturaService {

    /**
     * Lista las facturas asociadas a un ID de planta.
     *
     * @param idPlanta ID de la planta.
     * @return Lista opcional de facturas en formato DTO.
     * @throws FacturaNotFoundException Si no se encuentran facturas para el ID de planta proporcionado.
     */
    Optional<List<FacturaDTO>> listFacturaByIdPlanta(String idPlanta) throws FacturaNotFoundException;

    /**
     * Convierte una lista de entidades Factura a una lista de DTOs FacturaDTO.
     *
     * @param facturaList Lista de entidades Factura.
     * @return Lista de DTOs FacturaDTO.
     */
    List<FacturaDTO> listFacturaToListFacturaDTO(List<Factura> facturaList);

    /**
     * Busca facturas asociadas a un ID de planta y opcionalmente filtradas por fecha.
     *
     * @param idPlanta ID de la planta.
     * @param date     Fecha en formato String (opcional).
     * @return Lista opcional de facturas en formato DTO.
     * @throws FacturaNotFoundException Si no se encuentran facturas para los parámetros proporcionados.
     */
    Optional<List<FacturaDTO>> findByIdPlantaAndDate(String idPlanta, String date) throws FacturaNotFoundException;

    /**
     * Lista facturas filtradas por fecha.
     *
     * @param date Fecha en formato String.
     * @return Lista opcional de facturas en formato DTO.
     * @throws FacturaNotFoundException Si no se encuentran facturas para la fecha proporcionada.
     */
    Optional<List<FacturaDTO>> listFacturaByDate(String date) throws FacturaNotFoundException;

    /**
     * Agrega una nueva factura.
     *
     * @param facturaRequestDTO Datos de la factura a agregar.
     * @return DTO de la factura agregada.
     * @throws Exception Si ocurre algún error durante el proceso de guardado.
     */
    FacturaRequestDTO addFactura(FacturaRequestDTO facturaRequestDTO) throws Exception;

}
