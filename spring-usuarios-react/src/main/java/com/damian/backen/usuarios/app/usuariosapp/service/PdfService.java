package com.damian.backen.usuarios.app.usuariosapp.service;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {
    @Autowired
    private ResourceLoader resourceLoader;
    public byte[] generarPdf(Certificado certificado) {
        try {
            Resource resource = resourceLoader.getResource("classpath:1-laboratorio.pdf");
            InputStream inputStream = resource.getInputStream();
            PdfReader   reader = new PdfReader(inputStream);

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
    public byte[] generarPdfConDatos(Certificado certificado) {
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
            if (certificado.getConcepto().equals("Apto con restriccion que no interfieren en la labor")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(85, 234, 100)
                );
            }
            if (certificado.getConcepto().equals("Apto para trabajar en alturas")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(85, 223, 100)
                );
            }
            if (certificado.getConcepto().equals("Apto para manipulacion de alimentos")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(85, 211, 100)
                );
            }




            // del lado derecho

            if (certificado.getConcepto().equals("Examen de retiro satisfactorio")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(325, 245, 100)
                );
            }
            if (certificado.getConcepto().equals("Examen de retiro no satisfactorio")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(325, 234, 100)
                );
            }
            if (certificado.getConcepto().equals("Apto con restriccion que no interfieren en la labor 1")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(325, 223, 100)
                );
            }
            if (certificado.getConcepto().equals("Aplazado")){
                document.add(new Paragraph("x")
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFixedPosition(325, 211, 100)
                );
            }




            document.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public byte[] generarPdfConDatosCarnet(Certificado certificado) {
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
