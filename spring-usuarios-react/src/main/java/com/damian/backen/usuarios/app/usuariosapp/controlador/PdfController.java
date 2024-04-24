package com.damian.backen.usuarios.app.usuariosapp.controlador;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @GetMapping("/primero")
    public ResponseEntity<Resource> modifyPDF() {
        try {
            // Cargar el PDF existente
            PDDocument document = PDDocument.load(new File("1-laboratorio.pdf"));

            // Agregar texto al PDF
            PDPage page = document.getPage(0); // Obtener la primera página
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(100, 700); // Posición del texto en la página (coordenadas x, y)
            contentStream.showText("Texto agregado dinámicamente");
            contentStream.endText();
            contentStream.close();

            // Guardar el PDF modificado
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            document.close();

            // Convertir el PDF a un recurso de Spring
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            // Preparar la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "archivo_modificado.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}