package com.damian.backen.usuarios.app.usuariosapp.controlador;

import com.damian.backen.usuarios.app.usuariosapp.dto.UsuarioDto;
import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import com.damian.backen.usuarios.app.usuariosapp.endidad.Usuario;
import com.damian.backen.usuarios.app.usuariosapp.endidad.model.Reporte;
import com.damian.backen.usuarios.app.usuariosapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@CrossOrigin("*")
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private UsuarioService usuarioService;
    @PostMapping("/generar-pdf")
    @ResponseBody

    public ResponseEntity<byte[]> generarPdf(@RequestBody Reporte reporte) {
        System.out.println(reporte.toString());
        Boolean validar=false;
        try {
            if (reporte.desde.isEmpty() || reporte.hasta.isEmpty()) {
                reporte.desde = "Todos ";
                reporte.hasta = "Los Certificados";
            } else {
                validar= true;
                LocalDate desde = LocalDate.parse(reporte.desde, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate hasta = LocalDate.parse(reporte.hasta, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Formatear las fechas
                reporte.desde = desde.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                reporte.hasta = hasta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }

            // Crear un documento PDF
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Agregar contenido al PDF
            document.add(new Paragraph("Certificados realizados por Empleado"));
            if (validar){
                document.add(new Paragraph("Desde: " + reporte.desde));
                document.add(new Paragraph("Hasta: " + reporte.hasta));
            }

            Map<Long, Integer> usuariosAgrupados = new HashMap<>();
            reporte.certificados.forEach(e->{
                Long id = e.getUsuario();
                usuariosAgrupados.put(id, usuariosAgrupados.getOrDefault(id, 0) + 1);
            });


            // Agregar los usuarios y sus cantidades al PDF
            for (Map.Entry<Long, Integer> entry : usuariosAgrupados.entrySet()) {
                Usuario usuario = usuarioService.findById(entry.getKey()).orElse(null);
                if (usuario != null) {
                    document.add(new Paragraph("Usuario: " + usuario.getUsername() + " - Cantidad de Certificados: " + entry.getValue()));
                }
            }


            document.close();

            // Convertir el PDF a un array de bytes
            byte[] pdfBytes = baos.toByteArray();

            // Configurar la respuesta HTTP para la descarga del PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "estadisticas.pdf");
            headers.setContentLength(pdfBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            // Manejar errores
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
