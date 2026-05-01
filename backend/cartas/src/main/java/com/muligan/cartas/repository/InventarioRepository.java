package com.muligan.cartas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muligan.cartas.model.Inventario;
import com.muligan.cartas.model.TipoOferta;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findByUsuarioUsrId(Long usrId);
    List<Inventario> findByCardId(Long cardId);

    boolean existsByUsuarioUsrIdAndCartaCardIdAndEstadoAndTipo(
        Long usrId, 
        Long cardId, 
        String estado, 
        TipoOferta tipo
    );
}
