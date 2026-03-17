package com.muligan.cartas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muligan.cartas.model.Inventario;
import com.muligan.cartas.repository.InventarioRepository;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
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
}
