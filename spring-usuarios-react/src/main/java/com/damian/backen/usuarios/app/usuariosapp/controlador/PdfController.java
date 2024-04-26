package com.damian.backen.usuarios.app.usuariosapp.controlador;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import com.damian.backen.usuarios.app.usuariosapp.service.CertificadoService;
import com.damian.backen.usuarios.app.usuariosapp.service.PdfService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
@Autowired
    private PdfService pdfService;

@Autowired
private CertificadoService certificadoService;
    @GetMapping("/generar/{id}")
    public ResponseEntity<?> generarPDF(@PathVariable Long id) {

        return certificadoCLiente(id);
    }
    @GetMapping("/carnet/{id}")
    public ResponseEntity<?> generarPDF1(@PathVariable Long id) {

        return carnetCLiente(id);
    }
    @GetMapping("/medico/{id}")
    public ResponseEntity<?> generarPDF2(@PathVariable Long id) {
        return medico(id);
    }
    private ResponseEntity<?>medico(Long id){
        Optional<Certificado> optionalCertificado= certificadoService.findById(id);
        byte[] pdfBytes = pdfService.generarPdf(optionalCertificado.get());

        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "documento_con_datos.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    private ResponseEntity<?>certificadoCLiente(Long id){
        Optional<Certificado> optionalCertificado= certificadoService.findById(id);
        byte[] pdfBytes = pdfService.generarPdfConDatos(optionalCertificado.get());

        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "documento_con_datos.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    private ResponseEntity<?>carnetCLiente(Long id){
        Optional<Certificado> optionalCertificado= certificadoService.findById(id);
        byte[] pdfBytes = pdfService.generarPdfConDatosCarnet(optionalCertificado.get());

        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "documento_con_datos.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }



    // este tengo que modificar

}
