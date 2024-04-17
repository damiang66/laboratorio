package com.damian.backen.usuarios.app.usuariosapp.controlador;

import com.damian.backen.usuarios.app.usuariosapp.endidad.Cliente;
import com.damian.backen.usuarios.app.usuariosapp.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    private ResponseEntity<?>validar(BindingResult result){
        Map<String,Object>errores = new HashMap<>();
        result.getFieldErrors().forEach(e->{
            errores.put(e.getField(),"El campo: " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
    @GetMapping
    public ResponseEntity<?>findAll(){
        return ResponseEntity.ok(clienteService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Optional<Cliente>optionalCliente = clienteService.findById(id);
        if(optionalCliente.isPresent()){
            return ResponseEntity.ok(optionalCliente.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?>findByNombre(@PathVariable String nombre){
        List<Cliente> lista = clienteService.findByNombre(nombre);
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?>save (@Valid @RequestBody Cliente cliente,BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));

    }
    @PutMapping("/{id}")
    public ResponseEntity<?>update (@Valid @RequestBody Cliente cliente,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Cliente> clienteOptional = clienteService.findById(id);
        Cliente clienteDb = null;
        if (clienteOptional.isPresent()){
            clienteDb = clienteOptional.get();
            clienteDb.setDireccion(cliente.getDireccion());
            clienteDb.setNombre(cliente.getNombre());
            clienteDb.setEdad(cliente.getEdad());
            clienteDb.setDni(cliente.getDni());
            clienteDb.setEmail(cliente.getEmail());
            clienteDb.setCargo(cliente.getCargo());
            clienteDb.setTelefono(cliente.getTelefono());
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){
        Optional<Cliente>clienteOptional= clienteService.findById(id);
        if(clienteOptional.isPresent()){
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
