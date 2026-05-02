package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Metodo: GET
     * URL: localhost:8080/inventario
     * Proposito: Devolver todos los inventarios
     * @return los inventarios
     */
    @GetMapping
    public ResponseEntity<List<Inventario>> getInventarios() {
        List<Inventario> inventarios = inventarioService.getAllInventario();
        return ResponseEntity.ok(inventarios);
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/inventario/usuario/{usrid}
     * Proposito: Devolver las ofertas de un usuario
     * 
     * @param usrid del usuario cuyas ofertas queremos
     * @return lista de ofertas del usuario
     */
    @GetMapping("/usuario/{usrId}")
    public ResponseEntity<List<Inventario>> getInventarioByUsuario(@PathVariable Long usrId) {
        List<Inventario> inventario = inventarioService.getInventarioByUsuario(usrId);
        return ResponseEntity.ok(inventario);
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/inventario/carta/{cardid}
     * Proposito: Devolver las ofertas de una carta
     * 
     * @param cardid de la carta cuyas ofertas queremos ver
     * @return lista de ofertas de esa carta
     */
    @GetMapping("/carta/{cardid}")
    public ResponseEntity<List<Inventario>> getMethodName(@PathVariable Long cardid) {
        List<Inventario> inventario = inventarioService.getInventarioByCarta(cardid);
        return ResponseEntity.ok(inventario);
    }
    

    /**
     * Metodo: POST
     * URL: localhost:8080/inventario/agregar
     * Proposito: Agregar una carta al inventario de un usuario
     * 
     * @param request oferta que se hace
     * @param auth autenticación del usuario
     * @return mensaje de éxito o error
     */

    @PostMapping("/agregar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> agregarCarta(@RequestBody PeticionOferta request, Authentication auth) {

        Usuario usuario = (Usuario) auth.getPrincipal();
        String nombreUsuario = usuario.getNombreUsr();

        try {
            inventarioService.crearOferta(request, nombreUsuario);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    /**
     * Metodo: DELETE
     * URL: localhost:8080/inventario/{id}
     * Proposito: Eliminar una oferta del inventario
     * 
     * @param id de la oferta a eliminar
     * @return respuesta vacía (204) o error
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
}
