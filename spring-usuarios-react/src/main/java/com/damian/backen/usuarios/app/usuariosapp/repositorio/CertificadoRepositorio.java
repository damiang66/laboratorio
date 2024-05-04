package com.damian.backen.usuarios.app.usuariosapp.repositorio;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificadoRepositorio extends JpaRepository<Certificado,Long> {
    //Page<Certificado>paginar(Pageable pageable);
}
