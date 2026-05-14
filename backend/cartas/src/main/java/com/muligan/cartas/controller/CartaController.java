package com.muligan.cartas.controller;

import com.muligan.cartas.model.Carta;
import com.muligan.cartas.model.Empresa;
import com.muligan.cartas.model.Imagen;
import com.muligan.cartas.service.CartaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carta")
@Validated
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
    @GetMapping
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
    public ResponseEntity<Carta> getCartaById(@PathVariable Long id) {
        Carta carta = cartaService.getCartaById(id);
        if (carta != null) {
            return ResponseEntity.ok(carta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Método: GET
     * URL: localhost:8080/carta/{id}/imagen
     * Proposito: Devolver
     *
     * @param id de la carta cuya imagen se quiere sacar
     * @return imagen de la carta
     */
    @GetMapping("/{id}/imagen")
    public ResponseEntity<Imagen> getImagenFromCarta(@PathVariable Long id) {
        Imagen imagen = cartaService.getCartaById(id).getImagen();
        if (imagen != null) {
            return ResponseEntity.ok(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Método: GET
     * URL: localhost:8080/carta/marca/{empresa}
     * Proposito: Devolver las cartas de una marca especificada
     *
     * @param empresa de las cartas que se buscan
     * @return lista de cartas de esa empresa
     */
    @GetMapping("/marca/{empresa}")
    public ResponseEntity<List<Carta>> getCartaByEmpresa(@PathVariable String empresa) {
        List<Carta> todasCartas = cartaService.getAllCartas();
        List<Carta> cartasFiltradas = new ArrayList<>();
        Empresa marca = Empresa.valueOf(empresa.toUpperCase());

        for (Carta carta : todasCartas) {
            if (carta.getEmpresa() == marca) {
                cartasFiltradas.add(carta);
            }
        }

        if (cartasFiltradas.size() != 0) {
            return ResponseEntity.ok(cartasFiltradas);
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
    @PostMapping
    public ResponseEntity<Carta> createCarta(@RequestBody Carta carta) {
        Carta createdCarta = cartaService.createCarta(carta);
        return ResponseEntity.ok(createdCarta);
    }

    /**
     * Metodo: PUT
     * URL: localhost:8080/carta/{id}
     * Proposito: Actualizar una carta por su id
     *
     * @param id    de la carta a actualizar
     * @param carta con los nuevos datos
     * @return carta actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<Carta> updateCarta(@PathVariable Long id, @RequestBody Carta carta) {
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
    public ResponseEntity<Void> deleteCartaById(@PathVariable Long id) {
        cartaService.deleteCartaById(id);
        return ResponseEntity.noContent().build();
    }


}
