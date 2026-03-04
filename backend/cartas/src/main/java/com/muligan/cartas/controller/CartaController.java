package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.muligan.cartas.model.Carta;
import com.muligan.cartas.service.CartaService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/carta")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class CartaController {

    private final CartaService cartaService;

    public CartaController(CartaService cartaService) {
        this.cartaService = cartaService;
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/carta/
     * Proposito: Devolver todas las cartas
     * 
     * @return lista de cartas
     */
    @GetMapping("/")
    public ResponseEntity<List<Carta>> getAllCartas() {
        List<Carta> cartas = cartaService.getAllCartas();
        return ResponseEntity.ok(cartas);
    }

    /**
     * Metodo: GET
     * URL: localhost:8080/carta/{id}
     * Proposito: Devolver una carta por su id
     * 
     * @param id de la carta a devolver
     * @return carta con el id especificado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Carta> getCartaById(@PathVariable String id) {
        Carta carta = cartaService.getCartaById(id);
        if (carta != null) {
            return ResponseEntity.ok(carta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodo: POST
     * URL: localhost:8080/carta/
     * Proposito: Crear una nueva carta
     * 
     * @param carta a crear
     * @return carta creada
     */
    @PostMapping("/")
    public ResponseEntity<Carta> createCarta(@RequestBody Carta carta) {
        Carta createdCarta = cartaService.createCarta(carta);
        return ResponseEntity.ok(createdCarta);
    }

    /**
     * Metodo: PUT
     * URL: localhost:8080/carta/{id}
     * Proposito: Actualizar una carta por su id
     * 
     * @param id de la carta a actualizar
     * @param carta con los nuevos datos
     * @return carta actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<Carta> updateCarta(@PathVariable String id, @RequestBody Carta carta) {
        Carta updatedCarta = cartaService.updateCarta(id, carta);
        if (updatedCarta != null) {
            return ResponseEntity.ok(updatedCarta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodo: DELETE
     * URL: localhost:8080/carta/{id}
     * Proposito: Eliminar una carta por su id
     * 
     * @param id de la carta a eliminar
     * @return respuesta vacia
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartaById(@PathVariable String id) {
        cartaService.deleteCartaById(id);
        return ResponseEntity.noContent().build();
    }



}
