package com.muligan.cartas.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muligan.cartas.model.TipoUsuario;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getUsuarioByNombre(String nombre) {
        return usuarioRepository.findByNombreUsr(nombre);
    }

    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("EMAIL_DUPLICADO");
        }
        if (usuarioRepository.existsByNombreUsr(usuario.getNombreUsr())) {
            throw new RuntimeException("NOMBRE_USUARIO_DUPLICADO");
        }

        String passwordEncriptada = passwordEncoder.encode(usuario.getPasswd());
        usuario.setPasswd(passwordEncriptada);

        if (usuario.getTipo() == null) {
            usuario.setTipo(TipoUsuario.USR);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario u) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombreUsr(u.getNombreUsr());
            usuario.setEmail(u.getEmail());
            if (u.getPasswd() != null && !u.getPasswd().isEmpty()) {
                usuario.setPasswd(passwordEncoder.encode(u.getPasswd()));
            }
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    public Usuario updateReputacion(Long id, int nuevaReputacion) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setReputacion(nuevaReputacion);
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario autenticar(String email, String passwordEscrita) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USUARIO_NO_ENCONTRADO"));

        if (!passwordEncoder.matches(passwordEscrita, usuario.getPasswd())) {
            throw new RuntimeException("PASSWORD_INCORRECTA");
        }

        return usuario;
    }

    public void crearUsuarioDefault() {
        try {

            Usuario defNorm = new Usuario();
            defNorm.setNombreUsr("JohnDoe");
            defNorm.setEmail("johndoe@gmail.com");
            defNorm.setReputacion(0);
            defNorm.setPasswd("123456");
            defNorm.setTipo(TipoUsuario.USR);
            saveUsuario(defNorm);

            Usuario defAdmin = new Usuario();
            defAdmin.setNombreUsr("Administrador");
            defAdmin.setEmail("admin@gmail.com");
            defAdmin.setReputacion(99);
            defAdmin.setPasswd("coolassadmin");
            defAdmin.setTipo(TipoUsuario.ADMIN);
            saveUsuario(defAdmin);

        } catch (Exception e) {
            System.out.println("Los usuarios ya estaban creados.");
        }

    }
}
