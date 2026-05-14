package com.muligan.cartas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muligan.cartas.model.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long>{
    Imagen findByNombre(String nombre);

}
