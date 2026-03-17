package com.muligan.cartas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muligan.cartas.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findByUsuarioUsrId(Long usrId);
    
}
