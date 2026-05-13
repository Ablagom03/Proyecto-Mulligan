package com.muligan.cartas.controller;

import com.muligan.cartas.model.Comentario;
import com.muligan.cartas.service.ComentarioService;
import com.muligan.cartas.dto.PeticionComentario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.muligan.cartas.model.Usuario;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    /**
     * Crea un nuevo comentario para una oferta.
     * URL: POST localhost:8080/api/comentario
     * Body: { "inventarioId": 1, "texto": "Excelente venta", "tipo": "POSITIVO" }
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> crearComentario(
            @Valid @RequestBody PeticionComentario request,
            Authentication auth) {
        try {
            if (request.getInventarioId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "El ID de la oferta es requerido"));
            }
            if (request.getTexto() == null || request.getTexto().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El comentario no puede estar vacío"));
            }
            if (request.getTipo() == null || request.getTipo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El tipo de comentario es requerido"));
            }
            
            Usuario usuarioLogueado = (Usuario) auth.getPrincipal();
            
            Comentario comentario = comentarioService.crearComentario(
                    request.getInventarioId(),
                    usuarioLogueado.getUsrId(),
                    request.getTexto(),
                    request.getTipo()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtiene todos los comentarios recibidos por un usuario.
     * URL: GET localhost:8080/api/comentario/recibidos/{usrId}
     */
    @GetMapping("/recibidos/{usrId}")
    public ResponseEntity<List<Comentario>> obtenerComentariosRecibidos(@PathVariable Long usrId) {
        List<Comentario> comentarios = comentarioService.obtenerComentariosRecibidos(usrId);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * Obtiene todos los comentarios dejados por un usuario.
     * URL: GET localhost:8080/api/comentario/dejados/{usrId}
     */
    @GetMapping("/dejados/{usrId}")
    public ResponseEntity<List<Comentario>> obtenerComentariosDejaos(@PathVariable Long usrId) {
        List<Comentario> comentarios = comentarioService.obtenerComentariosDejaos(usrId);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * Obtiene todos los comentarios de una oferta específica.
     * URL: GET localhost:8080/api/comentario/oferta/{inventarioId}
     */
    @GetMapping("/oferta/{inventarioId}")
    public ResponseEntity<List<Comentario>> obtenerComentariosPorOferta(@PathVariable Long inventarioId) {
        List<Comentario> comentarios = comentarioService.obtenerComentariosPorOferta(inventarioId);
        return ResponseEntity.ok(comentarios);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errorMessage));
    }
}
