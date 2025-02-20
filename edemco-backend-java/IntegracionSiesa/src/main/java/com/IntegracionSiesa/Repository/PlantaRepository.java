package com.IntegracionSiesa.Repository;

import com.IntegracionSiesa.Entities.Planta;
import com.IntegracionSiesa.dto.PlantaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para realizar operaciones relacionadas con la entidad Planta.
 */
@Repository
public interface PlantaRepository extends JpaRepository<Planta, String> {

    /**
     * Obtiene una lista de todas las plantas registradas.
     *
     * @return Lista de objetos PlantaDto con los detalles de todas las plantas.
     */
    @Query("SELECT new com.IntegracionSiesa.dto.PlantaDto(p.idPlanta, p.asunto, p.centroCosto, p.nombrePlanta, p.urlImg, p.idOperador, p.cliente.idCliente, p.valorUnidad, p.porcentajeAumento) FROM Planta p")
    List<PlantaDto> findAllPlantas();

    /**
     * Obtiene el valor de la unidad asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return Valor de la unidad como un Double.
     */
    @Query("SELECT p.valorUnidad FROM Planta p WHERE p.idPlanta = :idPlanta")
    Double findValorUnidadByIdPlanta(String idPlanta);

    /**
     * Obtiene el nombre de una planta específica a partir de su identificador.
     *
     * @param idPlanta Identificador de la planta.
     * @return Nombre de la planta como un String.
     */
    @Query("SELECT p.nombrePlanta FROM Planta p WHERE p.idPlanta = :idPlanta")
    String findNombrePlantaByIdPlanta(String idPlanta);

    /**
     * Obtiene el identificador de una planta a partir de su nombre.
     *
     * @param nombrePlanta Nombre de la planta.
     * @return Identificador de la planta como un String.
     */
    @Query("SELECT p.idPlanta FROM Planta p WHERE p.nombrePlanta = :nombrePlanta")
    String findIdPlantaByNombrePlanta(String nombrePlanta);

    /**
     * Obtiene el identificador del operador asociado a una planta específica.
     *
     * @param idPlanta Identificador de la planta.
     * @return Identificador del operador como un Long.
     */
    @Query("SELECT p.idOperador FROM Planta p WHERE p.idPlanta = :idPlanta")
    Long findIdOperadorByIdPlanta(String idPlanta);

    /**
     * Verifica si una planta está registrada como parte de facturación especial.
     *
     * @param idPlanta Identificador de la planta.
     * @return Identificador de la planta como un String si pertenece a facturación especial.
     */
    @Query("SELECT p.idPlanta FROM Planta p INNER JOIN Cliente c ON p.cliente.idCliente = c.idCliente " +
            "INNER JOIN TipoCliente t ON c.tipoCliente.idTipoCliente = t.idTipoCliente " +
            "WHERE t.idTipoCliente = 2 AND p.idPlanta = :idPlanta")
    String verifyPlantaInFacturacionEspecial(String idPlanta);
}
