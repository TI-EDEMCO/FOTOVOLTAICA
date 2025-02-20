package com.IntegracionSiesa.Repository;

import com.IntegracionSiesa.Entities.Cliente;
import com.IntegracionSiesa.dto.ClienteDto;
import com.IntegracionSiesa.dto.PlantaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para realizar operaciones relacionadas con la entidad Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Obtiene una lista de todos los clientes.
     *
     * @return Lista de objetos ClienteDto que contienen información básica de los clientes.
     */
    @Query("SELECT new com.IntegracionSiesa.dto.ClienteDto(c.idCliente, c.nombreCliente, c.contrato," +
            "c.nit, c.tipoCliente.idTipoCliente) " +
            " FROM Cliente c")
    List<ClienteDto> listClientes();

    /**
     * Busca un cliente por su NIT.
     *
     * @param nit NIT del cliente que se desea buscar.
     * @return Lista de objetos ClienteDto con los detalles del cliente.
     */
    @Query("SELECT new com.IntegracionSiesa.dto.ClienteDto(c.idCliente, c.nombreCliente, c.contrato, " +
            "c.nit, c.tipoCliente.idTipoCliente)" +
            " FROM Cliente c WHERE c.idCliente = :nit")
    List<ClienteDto> findByNit(String nit);

    /**
     * Obtiene una lista de plantas asociadas a clientes especiales.
     * Los clientes especiales se identifican por un tipo de cliente específico.
     *
     * @return Lista de objetos PlantaDto con los detalles de las plantas asociadas a clientes especiales.
     */
    @Query("SELECT new com.IntegracionSiesa.dto.PlantaDto(p.idPlanta, p.asunto, p.centroCosto, p.nombrePlanta, p.urlImg, p.idOperador, p.cliente.idCliente, p.valorUnidad, p.porcentajeAumento)" +
            " FROM Planta p JOIN Cliente c ON p.cliente.idCliente = c.idCliente" +
            " JOIN TipoCliente t ON c.tipoCliente.idTipoCliente = t.idTipoCliente" +
            " WHERE t.idTipoCliente = 2")
    List<PlantaDto> findSpecialCustomers();

    /**
     * Busca los detalles de una planta específica asociada a un cliente especial.
     *
     * @param idPlanta Identificador de la planta.
     * @return Objeto PlantaDto con los detalles de la planta si pertenece a un cliente especial.
     */
    @Query("SELECT new com.IntegracionSiesa.dto.PlantaDto(p.idPlanta, p.asunto, p.centroCosto, p.nombrePlanta, p.urlImg, p.idOperador, p.cliente.idCliente, p.valorUnidad, p.porcentajeAumento)" +
            " FROM Planta p JOIN Cliente c ON p.cliente.idCliente = c.idCliente" +
            " JOIN TipoCliente t ON c.tipoCliente.idTipoCliente = t.idTipoCliente" +
            " WHERE p.idPlanta = :idPlanta AND t.idTipoCliente = 2")
    PlantaDto findEspecialCustomerByIdPlanta(String idPlanta);

    /**
     * Obtiene el identificador de un cliente basado en su NIT.
     *
     * @param nitCliente NIT del cliente que se desea buscar.
     * @return Identificador del cliente como un Long.
     */
    @Query("SELECT c.idCliente FROM Cliente c WHERE c.nit = :nitCliente")
    Long findIdClienteByNit(Integer nitCliente);

}
