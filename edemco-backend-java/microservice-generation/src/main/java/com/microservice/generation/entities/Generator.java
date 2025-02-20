package com.microservice.generation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "generacion")
@AllArgsConstructor
@NoArgsConstructor
public class Generator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_generacion")
    private Long idGeneracion;

    @Column(name = "generacion_actual")
    private Double generacionActual;

    @Column(name = "generacion_acumulado")
    private Double generacionAcumulado;

    @Column(name = "valor_unidad")
    private Double valorUnidad;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "diferencia_tarifa")
    private Double diferenciaTarifa;

    @Column(name = "ahorro_actual")
    private Double ahorroActual;

    @Column(name = "ahorro_acumulado")
    private Double ahorroAcumulado;

    @Column(name = "ahorro_codos_actual")
    private Double ahorroCodosActual;

    @Column(name = "ahorro_codos_acumulado")
    private Double ahorroCodosAcumulado;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "id_tarifa_operador")
    private Long idTarifaOperador;

    @Column(name = "id_planta")
    private String idPlanta;

}
