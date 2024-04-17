package com.damian.backen.usuarios.app.usuariosapp.service;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;

import java.util.List;
import java.util.Optional;

public interface CertificadoService {
    public List<Certificado>findAll();
    public Optional<Certificado>findById(Long id);
    public Certificado save(Certificado certificado);
    public void delete(Long id);
}
