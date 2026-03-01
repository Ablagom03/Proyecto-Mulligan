package com.muligan.cartas.repository;


import org.springframework.stereotype.Repository;

import com.muligan.cartas.model.Carta;

@Repository
public interface CartaRepository  extends org.springframework.data.jpa.repository.JpaRepository<Carta, Long> {

    

    
}
