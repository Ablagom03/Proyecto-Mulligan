package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.service.UsuarioService;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/usuario")
@Validated
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Metodo: Get
     * URL: localhost:8080/usuario/
     * Proposito: Devolver los usuarios
     * 
     * @return lista de cartas
     */
    @GetMapping
    public ResponseEntity <List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/usuario/{id}
     * Proposito: Devolver un usuario específico
     * 
     * @param id del usuario
     * @return usuario con el id especificado
     */
    @GetMapping("/{id}")    
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     /**
      * Metodo: POST
      * URL: localhost:8080/usuario/
      * Proposito: Crear un nuevo usuario
      * 
      * @param usuario a crear
      * @return usuario creado
      */
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(usuarioCreado);
    }

    /**
     * Metodo: PUT
     * URL: localhost:8080/usuario/{id}
     * Proposito: Actualizar un usuario por su id
     * 
     * @param id del usuario a actualizar
     * @param usuario con los datos nuevos
     * @return usuario actualizado
     */
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.updateUsuario(id,usuario);
        if (usuarioActualizado != null) {
          return ResponseEntity.ok(usuarioActualizado);  
        } else {
            return ResponseEntity.notFound().build();
        } 
    }

    /**
     * Metodo: DELETE
     * URL: localhost:8080/usuario/{id}
     * Proposito: Eliminar un usuario por su id
     * 
     * @param id del usuario a eliminar
     * @return respuesta vacía
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

     





    

}
