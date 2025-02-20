package com.Operadores.Repository;

import com.Operadores.Dto.OperadorDto;
import com.Operadores.Entities.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {

    /**
     * Busca un operador por su ID.
     *
     * @param idOperador ID del operador que se desea buscar.
     * @return Objeto Operador correspondiente al ID proporcionado.
     */
    @Query("SELECT o FROM Operador o WHERE idOperador = :idOperador")
    Operador findOperadorById(Long idOperador);

    /**
     * Obtiene una lista de todos los operadores en formato OperadorDto.
     *
     * @return Lista de objetos OperadorDto con los datos de todos los operadores.
     */
    @Query("SELECT new com.Operadores.Dto.OperadorDto(o.idOperador, o.nombreOperador, o.imgLogo) FROM Operador o")
    List<OperadorDto> findAllOperadores();

    /**
     * Busca un operador específico y lo retorna en formato OperadorDto.
     *
     * @param idOperador ID del operador que se desea buscar.
     * @return Objeto OperadorDto correspondiente al operador solicitado.
     */
    @Query("SELECT new com.Operadores.Dto.OperadorDto(o.idOperador, o.nombreOperador, o.imgLogo) FROM Operador o WHERE idOperador = :idOperador")
    OperadorDto findOperadorDtoById(Long idOperador);

}
