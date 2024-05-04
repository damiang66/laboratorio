package com.damian.backen.usuarios.app.usuariosapp.service;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CertificadoService {
    public List<Certificado>findAll();
    public Page<Certificado>paginar(Pageable pageable);
    public Optional<Certificado>findById(Long id);
    public Certificado save(Certificado certificado);
    public void delete(Long id);
}
