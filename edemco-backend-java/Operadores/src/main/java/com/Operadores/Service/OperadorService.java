package com.Operadores.Service;

import com.Operadores.Dto.OperadorDto;
import com.Operadores.Repository.OperadorRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    Logger LOGGER = Logger.getLogger(OperadorService.class);

    /**
     * Obtiene una lista de todos los operadores en formato OperadorDto.
     *
     * @return Lista de objetos OperadorDto con los datos de todos los operadores.
     * @throws NullPointerException Si ocurre un error al obtener los datos de los operadores.
     */
    public List<OperadorDto> listarOperadores() throws NullPointerException {
        LOGGER.info("Iniciando el listado de los operadores");
        return operadorRepository.findAllOperadores();
    }

    /**
     * Busca un operador específico por su ID y lo retorna en formato OperadorDto.
     *
     * @param idOperador ID del operador que se desea buscar.
     * @return Objeto OperadorDto correspondiente al operador solicitado.
     */
    public OperadorDto findOperadorDtoById(Long idOperador) {
        return operadorRepository.findOperadorDtoById(idOperador);
    }
}
