package com.muligan.cartas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muligan.cartas.model.Carta;

@Repository
public interface CartaRepository extends JpaRepository<Carta, String> {
    @Query("SELECT c FROM Carta c WHERE c.nombreCard = :nombreCard")
    Carta findByNombreCard(String nombreCard);

}
