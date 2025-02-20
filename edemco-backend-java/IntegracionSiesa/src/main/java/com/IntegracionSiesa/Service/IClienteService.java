package com.IntegracionSiesa.Service;

import com.IntegracionSiesa.dto.ClienteDto;
import com.IntegracionSiesa.dto.PlantaDto;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos del servicio relacionados con clientes y plantas.
 */
public interface IClienteService {

    /**
     * Obtiene una lista de todos los clientes registrados.
     *
     * @return Optional que contiene una lista de ClienteDto con la información de los clientes.
     */
    Optional<List<ClienteDto>> listClientes();

    /**
     * Busca un cliente por su NIT.
     *
     * @param nit NIT del cliente a buscar.
     * @return Optional que contiene una lista de ClienteDto con los detalles del cliente, si es encontrado.
     */
    Optional<List<ClienteDto>> findById(String nit);

    /**
     * Obtiene una lista de clientes especiales.
     *
     * @return Optional que contiene una lista de PlantaDto con la información de las plantas asociadas a clientes especiales.
     */
    Optional<List<PlantaDto>> findSpecialCustomers();

    /**
     * Busca información de una planta específica asociada a un cliente especial por su ID.
     *
     * @param idPlanta Identificador de la planta.
     * @return Optional que contiene un PlantaDto con los detalles de la planta, si es encontrada.
     */
    Optional<PlantaDto> findEspecialCustomerByIdPlanta(String idPlanta);

    /**
     * Obtiene el identificador de un cliente basado en su NIT.
     *
     * @param nitCliente NIT del cliente.
     * @return El identificador del cliente como un Long.
     */
    Long findIdClienteByNit(Integer nitCliente);
}
