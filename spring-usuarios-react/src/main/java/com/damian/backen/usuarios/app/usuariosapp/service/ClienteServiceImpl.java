package com.damian.backen.usuarios.app.usuariosapp.service;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Cliente;
import com.damian.backen.usuarios.app.usuariosapp.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Override
    public List<Cliente> findAll() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepositorio.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public void delete(Long id) {
        clienteRepositorio.deleteById(id);
    }

    @Override
    public List<Cliente> findByNombre(String nombre) {
        return clienteRepositorio.findByNombreContainingIgnoreCase(nombre);
    }
}
