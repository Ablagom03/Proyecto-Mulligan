package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muligan.cartas.model.Inventario;
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
        * @param usrid del usuario a devolver sus cartas
        * 
        * @return lista de cartas del inventario
        */
    @GetMapping("/usuario/{usrId}")
    public ResponseEntity<List<Inventario>> getInventarioByUsuario(@PathVariable Long usrId) {
        List<Inventario> inventario = inventarioService.getInventarioByUsuario(usrId);
        return ResponseEntity.ok(inventario);
    }
    
}
