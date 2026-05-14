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

    public Carta getCartaById(Long id) {
        Optional<Carta> carta = cartaRepository.findById(id);
        return carta.orElse(null);
    }

    public Carta createCarta(Carta carta) {
        return cartaRepository.save(carta);
    }

    public Carta updateCarta(Long id, Carta nuevaCarta) {

        Carta cartaExistente = cartaRepository.findById(id)
                .orElse(null);

        if (cartaExistente == null) {
            return null;
        }

        cartaExistente.setNombreCard(nuevaCarta.getNombreCard());
        cartaExistente.setDescripcion(nuevaCarta.getDescripcion());
        cartaExistente.setColeccion(nuevaCarta.getColeccion());
        cartaExistente.setEmpresa(nuevaCarta.getEmpresa());

        // ACTUALIZAR IMAGEN
        if (nuevaCarta.getImagen() != null) {

            if (cartaExistente.getImagen() != null) {

                cartaExistente.getImagen().setNombre(
                        nuevaCarta.getImagen().getNombre());

                cartaExistente.getImagen().setData(
                        nuevaCarta.getImagen().getData());

            } else {

                cartaExistente.setImagen(nuevaCarta.getImagen());
            }
        }

        return cartaRepository.save(cartaExistente);
    }

    public void deleteCartaById(Long id) {
        cartaRepository.deleteById(id);
    }
}
