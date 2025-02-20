package com.IntegracionSiesa.Service;

import com.IntegracionSiesa.dto.PlantaDto;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos del servicio relacionados con la gestión de plantas.
 */
public interface IPlantaService {

    /**
     * Obtiene una lista de todas las plantas registradas.
     *
     * @return Optional que contiene una lista de PlantaDto con los detalles de las plantas.
     */
    Optional<List<PlantaDto>> findAllPlantas();

    /**
     * Obtiene el valor de la unidad asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return Valor de la unidad como un Double.
     */
    Double findValorUnidadByIdPlanta(String idPlanta);

    /**
     * Obtiene el nombre de una planta específica a partir de su identificador.
     *
     * @param idPlanta Identificador de la planta.
     * @return Nombre de la planta como un String.
     */
    String findNombrePlantaByIdPlanta(String idPlanta);

    /**
     * Obtiene el identificador de una planta a partir de su nombre.
     *
     * @param nombrePlanta Nombre de la planta.
     * @return Identificador de la planta como un String.
     */
    String findIdPlantaByNombrePlanta(String nombrePlanta);

    /**
     * Obtiene el identificador del operador asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return Identificador del operador como un Long.
     */
    Long findIdOperadorByIdPlanta(String idPlanta);

    /**
     * Modifica los detalles de una lista de plantas.
     *
     * @param plantaDtoList Lista de objetos PlantaDto con los nuevos detalles de las plantas.
     * @return Lista actualizada de PlantaDto.
     */
    List<PlantaDto> modifyPlanta(List<PlantaDto> plantaDtoList);

    /**
     * Verifica si una planta está registrada como parte de facturación especial.
     *
     * @param idPlanta Identificador de la planta.
     * @return Un String que indica si la planta pertenece a facturación especial.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    String verifyPlantaInFacturacionEspecial(String idPlanta) throws Exception;
}
