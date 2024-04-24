package com.damian.backen.usuarios.app.usuariosapp.controlador;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import com.damian.backen.usuarios.app.usuariosapp.service.CertificadoService;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
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
    private ResourceLoader resourceLoader;
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
        byte[] pdfBytes = generarPdf(optionalCertificado.get());

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
        byte[] pdfBytes = generarPdfConDatos(optionalCertificado.get());

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
        byte[] pdfBytes = generarPdfConDatosCarnet(optionalCertificado.get());

        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "documento_con_datos.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
    private byte[] generarPdf(Certificado certificado) {
        try {
            Resource resource = resourceLoader.getResource("classpath:1-laboratorio.pdf");
            InputStream inputStream = resource.getInputStream();
            PdfReader reader = new PdfReader(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(reader, writer);
            Document document = new Document(pdfDocument);



            document.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] generarPdfConDatos(Certificado certificado) {
        try {
            Resource resource = resourceLoader.getResource("classpath:2-laboratorio.pdf");
            InputStream inputStream = resource.getInputStream();
            PdfReader reader = new PdfReader(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(reader, writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph(certificado.getCertificadoNumero())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(360, 650, 100)); // 36 is the margin-left, 750 is the margin-bottom, 100 is the width

            document.add(new Paragraph(certificado.getEmpresa())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(110, 610, 100)
            );
            document.add(new Paragraph(certificado.getCiudad())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(360, 610, 100)
            );
            document.add(new Paragraph(certificado.getFecha().toString())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(110, 590, 100)
            );
            document.add(new Paragraph(certificado.getDepartamento())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(360, 590, 100)
            );
            document.add(new Paragraph(certificado.getCliente().getNombre().toString())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(180, 530, 150)
            );
            document.add(new Paragraph(certificado.getCliente().getDni())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(180, 508, 100)
            );
            document.add(new Paragraph(certificado.getCliente().getCargo())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(360, 508, 100)
            );
            document.add(new Paragraph(certificado.getCliente().getEdad().toString())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(130, 480, 100)
            );
            document.add(new Paragraph(certificado.getCliente().getTelefono())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(250, 480, 100)
            );
            document.add(new Paragraph(certificado.getCliente().getDireccion())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(415, 480, 100)
            );
            document.add(new Paragraph(certificado.getCoprologico())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(150, 405, 100)
            );
            document.add(new Paragraph(certificado.getCultivo())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(415, 405, 100)
            );
            document.add(new Paragraph(certificado.getCoproCultivo())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(150, 385, 100)
            );
            document.add(new Paragraph(certificado.getKoh())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(415, 385, 100)
            );
            document.add(new Paragraph(certificado.getDiagnostico())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(70, 305, 200)
            );
            if (certificado.getConcepto().equals("Apto sin restriccion")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(85, 245, 100)
                );
            }
            if (certificado.getConcepto().equals("Examen de retiro satisfactorio")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(325, 245, 100)
                );
            }
            // agregar los if que faltan


            document.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // este tengo que modificar
    private byte[] generarPdfConDatosCarnet(Certificado certificado) {
        try {
            Resource resource = resourceLoader.getResource("classpath:3-laboratorio.pdf");
            InputStream inputStream = resource.getInputStream();
            PdfReader reader = new PdfReader(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(reader, writer);
            Document document = new Document(pdfDocument);



            document.add(new Paragraph(certificado.getCertificadoNumero())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(415, 410, 100)
            );
            document.add(new Paragraph(certificado.getCliente().getNombre())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(380, 380, 200)
            );
            document.add(new Paragraph(certificado.getCliente().getDni())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(450, 365, 200)
            );
            document.add(new Paragraph(certificado.getFecha().toString())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(81, 350, 100)
            );



            document.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
