package com.damian.backen.usuarios.app.usuariosapp.endidad;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "certificados")
public class Certificado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String certificadoNumero;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String ciudad;
    private String departamento;
    private String empresa;
    @ManyToOne
    private Cliente cliente;
    private Long idCliente;
    private String coprologico;
    private String coproCultivo;
    private String cultivo;
    private String koh;
    private String diagnostico;
    private String concepto;


}
