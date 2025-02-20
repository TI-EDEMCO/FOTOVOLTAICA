package com.IntegracionSiesa.Service;

import com.IntegracionSiesa.Repository.ClienteRepository;
import com.IntegracionSiesa.dto.ClienteDto;
import com.IntegracionSiesa.dto.PlantaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de cliente que proporciona métodos para realizar operaciones relacionadas con clientes y plantas.
 */
@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtiene una lista de todos los clientes registrados.
     *
     * @return Optional que contiene una lista de ClienteDto con la información de los clientes.
     */
    @Override
    public Optional<List<ClienteDto>> listClientes() {
        return Optional.of(clienteRepository.listClientes());
    }

    /**
     * Busca un cliente por su NIT.
     *
     * @param nit NIT del cliente a buscar.
     * @return Optional que contiene una lista de ClienteDto con los detalles del cliente, si es encontrado.
     */
    @Override
    public Optional<List<ClienteDto>> findById(String nit) {
        return Optional.ofNullable(clienteRepository.findByNit(nit));
    }

    /**
     * Obtiene una lista de clientes especiales.
     *
     * @return Optional que contiene una lista de PlantaDto con la información de las plantas asociadas a clientes especiales.
     */
    @Override
    public Optional<List<PlantaDto>> findSpecialCustomers() {
        return Optional.ofNullable(clienteRepository.findSpecialCustomers());
    }

    /**
     * Busca información de una planta específica asociada a un cliente especial por su ID.
     *
     * @param idPlanta Identificador de la planta.
     * @return Optional que contiene un PlantaDto con los detalles de la planta, si es encontrada.
     */
    @Override
    public Optional<PlantaDto> findEspecialCustomerByIdPlanta(String idPlanta) {
        return Optional.ofNullable(clienteRepository.findEspecialCustomerByIdPlanta(idPlanta));
    }

    /**
     * Obtiene el identificador de un cliente basado en su NIT.
     *
     * @param nitCliente NIT del cliente.
     * @return El identificador del cliente como un Long.
     */
    @Override
    public Long findIdClienteByNit(Integer nitCliente) {
        return clienteRepository.findIdClienteByNit(nitCliente);
    }
}
