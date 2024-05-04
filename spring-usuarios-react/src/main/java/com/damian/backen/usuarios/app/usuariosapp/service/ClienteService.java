package com.damian.backen.usuarios.app.usuariosapp.service;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    public List<Cliente>findAll();
    public Page<Cliente> paginar(Pageable pageable);
    public Optional<Cliente>findById(Long id);
    public Cliente save(Cliente cliente);
    public void delete(Long id);
    public List<Cliente>findByNombre(String nombre);

}
