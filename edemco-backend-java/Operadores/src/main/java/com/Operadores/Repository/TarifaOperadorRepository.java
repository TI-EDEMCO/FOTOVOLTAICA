package com.Operadores.Repository;

import com.Operadores.Dto.TarifaOperadorDto;
import com.Operadores.Entities.TarifaOperador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifaOperadorRepository extends JpaRepository<TarifaOperador, Long> {

    /**
     * Obtiene una lista de todas las tarifas de operadores en formato TarifaOperadorDto.
     *
     * @return Lista de objetos TarifaOperadorDto con los datos de todas las tarifas.
     */
    @Query("SELECT new com.Operadores.Dto.TarifaOperadorDto(t.idTarifaOperador, t.tarifaOperador, t.mes, t.anio, t.operador.idOperador) FROM TarifaOperador t")
    List<TarifaOperadorDto> findAllPlantas();

    /**
     * Obtiene una lista de las tarifas más recientes de cada operador.
     *
     * @return Lista de objetos TarifaOperadorDto con las tarifas más recientes agrupadas por operador.
     */
    @Query("SELECT new com.Operadores.Dto.TarifaOperadorDto(t.idTarifaOperador, t.tarifaOperador, t.mes, t.anio, t.operador.idOperador) " +
            "FROM TarifaOperador t WHERE t.idTarifaOperador IN (" +
            "SELECT MAX(t2.idTarifaOperador) FROM TarifaOperador t2 GROUP BY t2.operador.idOperador)")
    List<TarifaOperadorDto> findLastTarifasOperadores();

    /**
     * Busca la tarifa de un operador específico para un mes dado.
     *
     * @param idOperador ID del operador para el cual se desea buscar la tarifa.
     * @param mes Mes en el que se desea buscar la tarifa.
     * @return Objeto TarifaOperadorDto correspondiente a la tarifa más reciente
     *         del operador para el mes especificado.
     */
    @Query("SELECT new com.Operadores.Dto.TarifaOperadorDto(t.idTarifaOperador, t.tarifaOperador, t.mes, t.anio, t.operador.idOperador) FROM TarifaOperador t " +
            "WHERE t.operador.idOperador = :idOperador AND t.mes = :mes ORDER BY t.idTarifaOperador DESC LIMIT 1")
    TarifaOperadorDto findTarifaOperadorByIdOperadorAndMonth(Long idOperador, Integer mes);

}
