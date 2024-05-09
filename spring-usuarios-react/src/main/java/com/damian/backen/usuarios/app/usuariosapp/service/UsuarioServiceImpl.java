package com.damian.backen.usuarios.app.usuariosapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.damian.backen.usuarios.app.usuariosapp.dto.UsuarioDto;
import com.damian.backen.usuarios.app.usuariosapp.dto.mapper.DtoMapperUsuario;
import com.damian.backen.usuarios.app.usuariosapp.endidad.Rol;
import com.damian.backen.usuarios.app.usuariosapp.endidad.Usuario;
import com.damian.backen.usuarios.app.usuariosapp.repositorio.RolRepositorio;
import com.damian.backen.usuarios.app.usuariosapp.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServiceImpl implements UsuarioService {
   @Autowired
   private UsuarioRepositorio repositorio;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private RolRepositorio rolRepositio;

   @Override
   public List<UsuarioDto> findAll() {
      // return DtoMapperUsuario.getInstance().s repositorio.findAll();

      List<Usuario> usuarios = repositorio.findAll();

      return usuarios.stream().map(u->  DtoMapperUsuario.getInstance().setUsuario(u).build())
      .collect(Collectors.toList());
   }

   @Override
   public Optional<Usuario> findById(Long id) {
      /*
      return repositorio.findById(id).map(u->DtoMapperUsuario
      .getInstance()
      .setUsuario(u)
      .build());
      */
return  repositorio.findById(id);
   }

   @Override
   public UsuarioDto save(Usuario usuario) {
      if(usuario.getId() == null){
         String passwordBc = passwordEncoder.encode(usuario.getPassword());
         usuario.setPassword(passwordBc);
      }

      Optional<Rol> o = rolRepositio.findByNombre("ROLE_USER");
      Optional<Rol> admin = rolRepositio.findByNombre("ROLE_ADMIN");
      Optional<Rol> copado = rolRepositio.findByNombre("ROLE_COPADO");
      List<Rol> roles = new ArrayList();
      if (o.isPresent()) {
         roles.add(o.get());
      }
      if (usuario.getAdmin()){
         if (admin.isPresent()){
            roles.add(admin.get());
         }
      }
      if(usuario.getCopado()){
         roles.add(copado.get());
      }

      usuario.setRoles(roles);

      return DtoMapperUsuario.getInstance().setUsuario(repositorio.save(usuario)).build();
   }

   @Override
   public Usuario editar(Usuario usuario ,Long id) {
      System.out.println(usuario);
      Usuario usuariodb = repositorio.findById(id).get();
      usuariodb.setUsername(usuario.getUsername());
      String passwordBc = passwordEncoder.encode(usuario.getPassword());
      usuariodb.setPassword(passwordBc);

      usuariodb.setEmail(usuario.getEmail());
      return repositorio.save(usuariodb);
   }

   @Override
   public void delete(Long id) {
      repositorio.deleteById(id);
   }

   @Override
   public Optional<Usuario> findByNombre(String nombre) {
      return repositorio.findByUsername(nombre);
   }

}
