package com.muligan.cartas.service;

import com.muligan.cartas.model.Comentario;
import com.muligan.cartas.model.Inventario;
import com.muligan.cartas.model.TipoComentario;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.repository.ComentarioRepository;
import com.muligan.cartas.repository.InventarioRepository;
import com.muligan.cartas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final InventarioRepository inventarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public ComentarioService(ComentarioRepository comentarioRepository,
                             InventarioRepository inventarioRepository,
                             UsuarioRepository usuarioRepository,
                             UsuarioService usuarioService) {
        this.comentarioRepository = comentarioRepository;
        this.inventarioRepository = inventarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Comentario crearComentario(Long inventarioId, Long usuarioCompradorId, 
                                       String texto, String tipo) {
        
        Inventario inventario = inventarioRepository.findById(inventarioId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
        
        Usuario usuarioComprador = usuarioRepository.findById(usuarioCompradorId)
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado"));
        
        Usuario usuarioVendedor = inventario.getUsuario();
        
        Comentario comentario = new Comentario();
        comentario.setTexto(texto);
        comentario.setTipo(TipoComentario.valueOf(tipo.toUpperCase()));
        comentario.setUsuarioComprador(usuarioComprador);
        comentario.setUsuarioVendedor(usuarioVendedor);
        comentario.setInventario(inventario);
        
        Comentario comentarioGuardado = comentarioRepository.save(comentario);
        
        // Actualizar reputación del vendedor
        int cambioReputacion = comentario.getTipo() == TipoComentario.POSITIVO ? 1 : -1;
        int nuevaReputacion = usuarioVendedor.getReputacion() + cambioReputacion;
        usuarioService.updateReputacion(usuarioVendedor.getUsrId(), nuevaReputacion);
        
        return comentarioGuardado;
    }

    public List<Comentario> obtenerComentariosRecibidos(Long usrId) {
        return comentarioRepository.findByUsuarioVendedorUsrId(usrId);
    }

    public List<Comentario> obtenerComentariosDejaos(Long usrId) {
        return comentarioRepository.findByUsuarioCompradorUsrId(usrId);
    }

    public List<Comentario> obtenerComentariosPorOferta(Long inventarioId) {
        return comentarioRepository.findByInventarioId(inventarioId);
    }

    public Optional<Comentario> obtenerComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }
}
