package com.muligan.cartas.service;

import org.springframework.stereotype.Service;

import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public Usuario updateUsuario(Long id, Usuario u) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setNombreUsr(u.getNombreUsr());
            usuario.setEmail(u.getEmail());
            usuario.setPasswd(u.getPasswd());
            return usuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo no encontrado", e);
        }
    }

    public Usuario autenticar(String email, String passwd) {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPasswd().equals(hashMD5(passwd))) {
            return user.get();
        }
        return null;
    }
}
