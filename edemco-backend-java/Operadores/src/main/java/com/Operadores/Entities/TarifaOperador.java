package com.Operadores.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tarifa_operadores")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarifaOperador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa_operador")
    private Long idTarifaOperador;

    @Column(name = "tarifa_operador")
    private Double tarifaOperador;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "anio")
    private Integer anio;

    @ManyToOne
    @JoinColumn(name = "id_operador")
    private Operador operador;

}
