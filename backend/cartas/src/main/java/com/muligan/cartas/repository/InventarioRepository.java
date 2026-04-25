package com.muligan.cartas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muligan.cartas.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // llevo 2 horas solucionando un: Caused by:
    // org.springframework.data.core.PropertyReferenceException: No property 'usrId'
    // found for type 'Inventario'
    @Query("SELECT i FROM Inventario i WHERE i.usuario.usrId = :usrId")
    List<Inventario> findByUsuarioUsrId(@Param("usrId") Long usrId);

    @Query("SELECT COUNT(i) > 0 FROM Inventario i WHERE i.usuario.usrId = :usrId AND i.carta.cardId = :cardId AND i.estado = :estado")
    boolean existsByUsrIdAndCardIdAndEstado(@Param("usrId") Long usrId, @Param("cardId") Long cardId,
            @Param("estado") String estado);

}
