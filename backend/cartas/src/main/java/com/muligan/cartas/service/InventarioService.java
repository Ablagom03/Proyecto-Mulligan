package com.muligan.cartas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muligan.cartas.dto.PeticionOferta;
import com.muligan.cartas.model.Carta;
import com.muligan.cartas.model.Inventario;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.repository.CartaRepository;
import com.muligan.cartas.repository.InventarioRepository;
import com.muligan.cartas.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final CartaRepository cartaRepository;
    private final UsuarioRepository usuarioRepository;

    public InventarioService(InventarioRepository inventarioRepository, CartaRepository cartaRepository, UsuarioRepository usuarioRepository) {
        this.inventarioRepository = inventarioRepository;
        this.cartaRepository = cartaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

    public List<Inventario> getInventarioByUsuario(Long usrId) {
        return inventarioRepository.findByUsuarioUsrId(usrId);
    }

    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }


    public void deleteInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

    @Transactional
public Inventario crearOferta(PeticionOferta request, String nombreUsuario) {
    
    Usuario usuario = usuarioRepository.findByNombreUsr(nombreUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Carta carta = cartaRepository.findByNombreCard(request.getNombreCard())
            .orElseThrow(() -> new RuntimeException("Carta no encontrada"));
    boolean existe = inventarioRepository.existsByUsuarioUsrIdAndCartaCardIdAndEstadoAndTipo(
            usuario.getUsrId(), 
            carta.getCardId(), 
            request.getEstado(),
            request.getTipo() 
    );

    if (existe) {
        throw new RuntimeException("Ya existe una oferta de tipo " + request.getTipo() + " para esta carta en ese estado.");
    }

    Inventario inventario = new Inventario();
    
    inventario.setUsuario(usuario);
    inventario.setCarta(carta);
    inventario.setEstado(request.getEstado());
    inventario.setCopias(request.getCopias());
    inventario.setValor(request.getValor());
    inventario.setTipo(request.getTipo()); 

    return inventarioRepository.save(inventario);
}
}
