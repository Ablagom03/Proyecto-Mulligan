package com.muligan.cartas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.muligan.cartas.model.Carta;
import com.muligan.cartas.repository.CartaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/")
public class CartaController {
    
    @Autowired
    private CartaRepository cartaRepository;

    @GetMapping("/carta/{id}")
    public Carta mostrarCarta(@RequestParam String id) {
        return cartaRepository.findById(Long.parseLong(id)).orElse(null);
    }
    
}

