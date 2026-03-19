package com.muligan.cartas.service;

import org.springframework.stereotype.Service;

import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.repository.UsuarioRepository;
import java.util.List;
 



@Service
public class UsuarioService {
  
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long usrId) {
        return usuarioRepository.findByUsrId(usrId);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /*public Usuario updateUsuario(Long id, Usuario usuario) {
      //TODO crear esto
    }*/

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
