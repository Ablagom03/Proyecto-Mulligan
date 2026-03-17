package com.muligan.cartas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muligan.cartas.model.Carta;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {
    Carta findByNombreCard(String nombreCard);

}
