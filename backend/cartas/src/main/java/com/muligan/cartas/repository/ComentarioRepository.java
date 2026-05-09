package com.muligan.cartas.repository;

import com.muligan.cartas.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    // Obtener comentarios recibidos por un usuario (vendedor)
    List<Comentario> findByUsuarioVendedorUsrId(Long usrId);
    
    // Obtener comentarios dejados por un usuario (comprador)
    List<Comentario> findByUsuarioCompradorUsrId(Long usrId);
    
    // Obtener comentarios de una oferta específica
    List<Comentario> findByInventarioId(Long inventarioId);
}
