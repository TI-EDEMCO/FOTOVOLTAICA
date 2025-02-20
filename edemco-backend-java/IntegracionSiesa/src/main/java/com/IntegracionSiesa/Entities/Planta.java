package com.IntegracionSiesa.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planta {

    @Id
    @Column(name = "id_planta")
    private String idPlanta;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "centro_costos")
    private String centroCosto;

    @Column(name = "nombre_planta")
    private String nombrePlanta;

    @Column(name = "url_img")
    private String urlImg;

    @Column(name = "id_operador")
    private Long idOperador;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "valor_unidad")
    private Double valorUnidad;

    @Column(name = "porcentaje_aumento")
    private Double porcentajeAumento;




}
