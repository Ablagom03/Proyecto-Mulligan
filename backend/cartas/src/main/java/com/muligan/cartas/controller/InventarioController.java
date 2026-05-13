package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.muligan.cartas.dto.InventarioUpdateDTO;
import com.muligan.cartas.dto.PeticionOferta;
import com.muligan.cartas.model.Inventario;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.service.InventarioService;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /**
     * Obtiene todos los registros de inventario.
     * URL: GET localhost:8080/api/inventario
     */
    @GetMapping
    public ResponseEntity<List<Inventario>> getInventarios() {
        List<Inventario> inventarios = inventarioService.getAllInventario();
        return ResponseEntity.ok(inventarios);
    }

    /**
     * Obtiene los detalles de una oferta específica por su ID.
     * URL: GET localhost:8080/api/inventario/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getOfertaById(@PathVariable Long id) {
        return inventarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Devuelve todas las ofertas publicadas por un usuario específico.
     * URL: GET localhost:8080/api/inventario/usuario/{usrId}
     */
    @GetMapping("/usuario/{usrId}")
    public ResponseEntity<List<Inventario>> getInventarioByUsuario(@PathVariable Long usrId) {
        List<Inventario> inventario = inventarioService.getInventarioByUsuario(usrId);
        return ResponseEntity.ok(inventario);
    }

    /**
     * Devuelve todas las ofertas (Venta/Compra) asociadas a una carta específica.
     * URL: GET localhost:8080/api/inventario/carta/{cardid}
     */
    @GetMapping("/carta/{cardid}")
    public ResponseEntity<List<Inventario>> getInventarioByCarta(@PathVariable Long cardid) {
        List<Inventario> inventario = inventarioService.getInventarioByCarta(cardid);
        return ResponseEntity.ok(inventario);
    }

    /**
     * Agrega una nueva carta u oferta al inventario del usuario autenticado.
     * URL: POST localhost:8080/api/inventario/agregar
     */
    @PostMapping("/agregar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> agregarCarta(@RequestBody PeticionOferta request, Authentication auth) {
        Usuario usuario = (Usuario) auth.getPrincipal();
        try {
            inventarioService.crearOferta(request, usuario.getNombreUsr());
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * Actualiza los datos de una oferta (precio, copias, estado).
     * URL: PUT localhost:8080/api/inventario/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> actualizarOferta(@PathVariable Long id, @RequestBody InventarioUpdateDTO dto) {
        try {
            Inventario actualizado = inventarioService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Elimina una oferta del inventario personal. 
     * Valida que el usuario autenticado sea el propietario de la oferta.
     * URL: DELETE localhost:8080/api/inventario/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id, Authentication auth) {
        Usuario usuario = (Usuario) auth.getPrincipal();
        try {
            inventarioService.eliminarOferta(id, usuario.getUsrId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Procesa la eliminación de una oferta debido a una transacción de mercado.
     * A diferencia del método anterior, este permite la eliminación por parte de un tercero (comprador).
     * URL: DELETE localhost:8080/api/inventario/transaccion/{id}
     */
    @DeleteMapping("/transaccion/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> procesarTransaccion(@PathVariable Long id) {
        try {
            inventarioService.eliminarPorCompra(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}