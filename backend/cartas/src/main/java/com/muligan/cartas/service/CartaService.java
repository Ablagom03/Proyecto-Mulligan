package com.muligan.cartas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.muligan.cartas.model.Carta;
import com.muligan.cartas.repository.CartaRepository;

@Service
public class CartaService {
    private final CartaRepository cartaRepository;
    
    public CartaService(CartaRepository cartaRepository) {
        this.cartaRepository = cartaRepository;
    }

    public List<Carta> getAllCartas() {
        return cartaRepository.findAll();
    }

    public Carta getCartaById(Integer id) {
        Optional<Carta> carta = cartaRepository.findById(id);
        return carta.orElse( null);
    }

    public Carta createCarta(Carta carta) {
        return cartaRepository.save(carta);
    }

    public Carta updateCarta(Integer id, Carta d) {
        Optional<Carta> optionalCarta = cartaRepository.findById(id);
        if (optionalCarta.isPresent()) {
            Carta carta = optionalCarta.get();
            carta.setNombreCard(d.getNombreCard());
            carta.setDescripcion(d.getDescripcion());
            carta.setColeccion(d.getColeccion());
            carta.setEmpresa(d.getEmpresa());
            return cartaRepository.save(carta);
        } else {
            return null;
        }
    }

    public void deleteCartaById(Integer id) {
        cartaRepository.deleteById(id);
    }
}
