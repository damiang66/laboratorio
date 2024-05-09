package com.damian.backen.usuarios.app.usuariosapp.controlador;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Certificado;
import com.damian.backen.usuarios.app.usuariosapp.service.CertificadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/certificados")
public class CertificadoController {
    @Autowired
    private CertificadoService certificadoService;
    private ResponseEntity<?>validar(BindingResult result){
        Map<String,Object> errores = new HashMap<>();
      result.getFieldErrors().forEach(e->{
          errores.put(e.getField(),"EL campo: " + e.getField() + " " + e.getDefaultMessage()  );
      });
      return ResponseEntity.badRequest().body(errores);
    }
    @GetMapping("/paginar/{page}")
    public ResponseEntity<?>paginar(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,1);
        return ResponseEntity.ok(certificadoService.paginar(pageable));
    }
    @GetMapping
    public ResponseEntity<?>findAll(){
        return ResponseEntity.ok().body(certificadoService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Optional<Certificado> certificadoOptional = certificadoService.findById(id);
        if(certificadoOptional.isPresent()){
            return ResponseEntity.ok(certificadoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?>save(@Valid @RequestBody Certificado certificado,BindingResult result){
        System.out.println(certificado);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificadoService.save(certificado));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>editar(@Valid @RequestBody Certificado certificado,BindingResult result,@PathVariable Long id){
    Optional<Certificado>certificadoOptional = certificadoService.findById(id);
    Certificado certificadoDb = null;
        System.out.println("entre en el put");
    if(certificadoOptional.isPresent()){
        // falta agregar mas campos para editars
        certificadoDb = certificadoOptional.get();
        certificadoDb.setCertificadoNumero(certificado.getCertificadoNumero());
        certificadoDb.setFecha(certificado.getFecha());
        certificadoDb.setCiudad(certificado.getCiudad());
        certificadoDb.setDepartamento(certificado.getDepartamento());
        certificadoDb.setEmpresa(certificado.getEmpresa());
        certificadoDb.setCliente(certificado.getCliente());
        certificadoDb.setIdCliente(certificado.getIdCliente());
        certificadoDb.setCoprologico(certificado.getCoprologico());
        certificadoDb.setCoproCultivo(certificado.getCoproCultivo());
        certificadoDb.setCultivo(certificado.getCultivo());
        certificadoDb.setKoh(certificado.getKoh());
        certificadoDb.setDiagnostico(certificado.getDiagnostico());
        certificadoDb.setConcepto(certificado.getConcepto());
        return ResponseEntity.status(HttpStatus.CREATED).body(certificadoService.save(certificadoDb));
    }
    return  ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminar(@PathVariable Long id){
        certificadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
