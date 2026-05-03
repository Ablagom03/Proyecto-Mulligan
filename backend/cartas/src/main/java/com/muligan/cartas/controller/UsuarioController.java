package com.muligan.cartas.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.service.UsuarioService;

import jakarta.validation.Valid;

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
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
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
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(id);

        return usuarioOpt
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/usuario/{nombre}
     * Proposito: Devolver un usuario específico por su nombre
     * 
     * @param nombre del usuario
     * @return usuario con el nombre especificado
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> getUsuarioByNombre(@PathVariable String nombre) {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioByNombre(nombre);

        return usuarioOpt
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioCreado = usuarioService.saveUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (RuntimeException e) {
            String mensaje = e.getMessage();

            if ("EMAIL_DUPLICADO".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Este correo electrónico ya está registrado."));
            }

            if ("NOMBRE_USUARIO_DUPLICADO".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "El nombre de usuario ya está en uso."));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo crear el usuario. Revisa los datos."));
        }
    }

    /**
     * Metodo: PUT
     * URL: localhost:8080/usuario/{id}
     * Proposito: Actualizar un usuario por su id
     * 
     * @param id      del usuario a actualizar
     * @param usuario con los datos nuevos
     * @return usuario actualizado
     */

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable long id, @Valid @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuario);
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
