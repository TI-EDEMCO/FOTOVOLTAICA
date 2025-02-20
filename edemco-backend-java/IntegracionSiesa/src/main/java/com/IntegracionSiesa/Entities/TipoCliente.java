package com.IntegracionSiesa.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "tipo_cliente")
@AllArgsConstructor
@NoArgsConstructor
public class TipoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cliente")
    private Long idTipoCliente;

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    @OneToMany(targetEntity = Cliente.class, mappedBy = "tipoCliente", fetch = FetchType.LAZY)
    private List<Cliente> clientes;

}
