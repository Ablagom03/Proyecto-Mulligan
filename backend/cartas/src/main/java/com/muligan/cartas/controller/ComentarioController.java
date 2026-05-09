package com.muligan.cartas.controller;

import com.muligan.cartas.model.Comentario;
import com.muligan.cartas.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.muligan.cartas.model.Usuario;

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
            @RequestBody Map<String, Object> request,
            Authentication auth) {
        try {
            Usuario usuarioLogueado = (Usuario) auth.getPrincipal();
            Long inventarioId = Long.parseLong(request.get("inventarioId").toString());
            String texto = request.get("texto").toString();
            String tipo = request.get("tipo").toString();
            
            Comentario comentario = comentarioService.crearComentario(
                    inventarioId,
                    usuarioLogueado.getUsrId(),
                    texto,
                    tipo
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
        } catch (Exception e) {
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
}
