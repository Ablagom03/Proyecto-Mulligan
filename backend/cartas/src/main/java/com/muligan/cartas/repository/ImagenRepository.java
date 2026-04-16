package com.muligan.cartas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muligan.cartas.model.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, Long>{
    Imagen findByNombre(String nombre);

}
