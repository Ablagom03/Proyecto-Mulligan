package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
     * URL: localhost:8080/inventario/usuario/{usrid}
     * Proposito: Devolver todas las cartas de un usuario
     * 
     * @param usrid del usuario a devolver sus cartas
     * 
     * @return lista de cartas del inventario
     */
    @GetMapping("/usuario/{usrId}")
    public ResponseEntity<List<Inventario>> getInventarioByUsuario(@PathVariable Long usrId) {
        List<Inventario> inventario = inventarioService.getInventarioByUsuario(usrId);
        return ResponseEntity.ok(inventario);
    }

    /**
     * Metodo: POST
     * URL: localhost:8080/inventario/agregar
     * Proposito: Agregar una carta al inventario de un usuario
     * 
     * @param inventario con los datos de la carta a agregar
     * @return mensaje de éxito o error
     */

    @PostMapping("/agregar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> agregarCarta(@RequestBody PeticionOferta request, Authentication auth) {

        Usuario usuario = (Usuario) auth.getPrincipal();
        String nombreUsuario = usuario.getNombreUsr();

        try {
            inventarioService.crearOferta(request, nombreUsuario);
            return ResponseEntity.status(201).body("Carta agregada al inventario");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
