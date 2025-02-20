package com.Operadores.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "operador")
@AllArgsConstructor
@NoArgsConstructor
public class Operador{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operador")
    private Long idOperador;

    @Column(name = "nombre_operador")
    private String nombreOperador;

    @Column(name = "img_logo")
    private String imgLogo;

    @OneToMany(targetEntity = TarifaOperador.class,fetch = FetchType.LAZY, mappedBy = "operador")
    private List<TarifaOperador> tarifaOperadores;

}
