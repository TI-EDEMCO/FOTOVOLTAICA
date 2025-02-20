package com.microservice.factura.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "factura")
@AllArgsConstructor
@NoArgsConstructor
public class Factura {

    @Id
    @Column(name = "id_factura")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @Column(name = "fecha_inicial")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicial;

    @Column(name = "fecha_final")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinal;

    @Column(name = "dias_facturados")
    private Integer diasFacturados;

    @Column(name = "cufe")
    private String cufe;

    @Column(name = "fecha_pago")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaPago;

    @Column(name = "fecha_dian")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaDian;

    @Column(name = "pdf")
    private String pdf;

    @Column(name = "numero_factura")
    private String numeroFactura;

    @Column(name = "id_planta")
    private String idPlanta;

    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "concepto_facturado")
    private String conceptoFacturado;

}
