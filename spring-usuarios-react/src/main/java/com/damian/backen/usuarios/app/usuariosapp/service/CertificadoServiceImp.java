package com.damian.backen.usuarios.app.usuariosapp.service;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import com.damian.backen.usuarios.app.usuariosapp.endidad.Cliente;
import com.damian.backen.usuarios.app.usuariosapp.endidad.Usuario;
import com.damian.backen.usuarios.app.usuariosapp.repositorio.CertificadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CertificadoServiceImp implements CertificadoService{
    @Autowired
    private CertificadoRepositorio certificadoRepositorio;
    @Autowired
    private ClienteService clienteService;
    @Override
    public List<Certificado> findAll() {
        return certificadoRepositorio.findAll();
    }

    @Override
    public Page<Certificado> paginar(Pageable pageable) {
        return certificadoRepositorio.findAll(pageable);
    }

    @Override
    public Optional<Certificado> findById(Long id) {
        return certificadoRepositorio.findById(id);
    }

    @Override
    public Certificado save(Certificado certificado) {
        Cliente cliente = null;
        //Usuario usuario = null;

        Optional<Cliente>optionalCliente = clienteService.findById(certificado.getIdCliente());
        cliente = optionalCliente.get();
        certificado.setCliente(cliente);

        return certificadoRepositorio.save(certificado);
    }

    @Override
    public void delete(Long id) {
        certificadoRepositorio.deleteById(id);
    }
}
