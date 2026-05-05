package com.muligan.cartas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muligan.cartas.service.PrecioMercadoService;

@RestController
@RequestMapping("/precios")
public class PrecioMercadoController {

    private final PrecioMercadoService precioMercadoService;

    public PrecioMercadoController(PrecioMercadoService precioMercadoService) {
        this.precioMercadoService = precioMercadoService;
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/precios/externo?nombre=Pot of Greed&empresa=YUGIOH
     * Proposito: Devolver el valor comercial de una carta
     */
    @GetMapping("/externo")
    public ResponseEntity<Map<String, String>> getPrecio(
            @RequestParam String nombre, 
            @RequestParam String empresa) {
        
        String precio = precioMercadoService.obtenerPrecioComercial(nombre, empresa);
        
        Map<String, String> response = new HashMap<>();
        response.put("precio", precio);
        
        return ResponseEntity.ok(response);
    }
}