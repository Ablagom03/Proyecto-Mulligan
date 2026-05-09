package com.muligan.cartas.service;

import com.muligan.cartas.dto.InventarioUpdateDTO;
import com.muligan.cartas.dto.PeticionOferta;
import com.muligan.cartas.model.Carta;
import com.muligan.cartas.model.Inventario;
import com.muligan.cartas.model.TipoOferta;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.repository.CartaRepository;
import com.muligan.cartas.repository.InventarioRepository;
import com.muligan.cartas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final CartaRepository cartaRepository;
    private final UsuarioRepository usuarioRepository;

    public InventarioService(InventarioRepository inventarioRepository, 
                             CartaRepository cartaRepository,
                             UsuarioRepository usuarioRepository) {
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

    public List<Inventario> getInventarioByCarta(Long cardid) {
        return inventarioRepository.findByCartaCardId(cardid);
    }

    public Optional<Inventario> getById(Long id) {
        return inventarioRepository.findById(id);
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
                request.getTipo());

        if (existe) {
            throw new RuntimeException(
                    "Ya existe una oferta de tipo " + request.getTipo() + " para esta carta en ese estado.");
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

    @Transactional
    public Inventario actualizar(Long id, InventarioUpdateDTO dto) {
        Inventario oferta = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        if (dto.getTipo() != null)
            oferta.setTipo(TipoOferta.valueOf(dto.getTipo()));
        }
        if (dto.getValor() != null) {
            oferta.setValor(dto.getValor());
        }
        if (dto.getEstado() != null) {
            oferta.setEstado(dto.getEstado());
        }
        if (dto.getCopias() != null) {
            oferta.setCopias(dto.getCopias());
        if (dto.getNombreCard() != null) {
            Carta nuevaCarta = cartaRepository.findByNombreCard(dto.getNombreCard())
                    .orElseThrow(() -> new RuntimeException("La carta seleccionada no existe"));
            oferta.setCarta(nuevaCarta);
        }

        return inventarioRepository.save(oferta);
    }

    /**
     * Elimina una oferta validando que el usuario sea el dueño.
     * Se usa para gestión de perfil personal.
     */
    @Transactional
    public void eliminarOferta(Long inventarioId, Long usrId) {
        Inventario inv = inventarioRepository.findById(inventarioId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        if (!inv.getUsuario().getUsrId().equals(usrId)) {
            throw new RuntimeException("No tienes permiso para eliminar esta oferta");
        }

        inventarioRepository.delete(inv);
    }

    /**
     * Elimina una oferta sin validar el dueño.
     * Se usa exclusivamente para procesar compras/ventas entre usuarios.
     */
    @Transactional
    public void eliminarPorCompra(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("La oferta con ID " + id + " ya no está disponible");
        }
        inventarioRepository.deleteById(id);
    }
}