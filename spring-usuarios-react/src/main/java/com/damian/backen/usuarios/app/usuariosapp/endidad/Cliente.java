package com.damian.backen.usuarios.app.usuariosapp.endidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String dni;
    private Integer edad;
    private String telefono;
    private String cargo;
    private String direccion;
    private String email;
}
