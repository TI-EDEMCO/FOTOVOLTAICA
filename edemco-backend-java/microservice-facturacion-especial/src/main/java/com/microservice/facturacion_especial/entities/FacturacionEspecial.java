package com.microservice.facturacion_especial.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "facturacion_especial")
@AllArgsConstructor
@NoArgsConstructor
public class FacturacionEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facturacion_especial")
    private Long idFacturacionEspecial;

    @Column(name = "id_planta")
    private String idPlanta;

    @Column(name = "cantidad_kwh")
    private Float cantidadkWh;

    private Float excedente;

    @Column(name = "costo_agregado")
    private Float costoAgregado;

    @Column(name = "valor_exportacion")
    private float valorExportacion;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "mes")
    private Integer mes;

}
