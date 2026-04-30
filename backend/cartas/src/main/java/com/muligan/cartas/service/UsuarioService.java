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

    public Optional<Usuario> getUsuarioById(Long usrId) {
        return usuarioRepository.findByUsrId(usrId);
    }

    public Usuario saveUsuario(Usuario usuario) {
        usuario.setPasswd(passwordEncoder.encode(usuario.getPasswd()));
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
    public Usuario autenticar(String email, String passwdPlano) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();
            if (passwordEncoder.matches(passwdPlano, usuario.getPasswd())) {
                return usuario;
            }
        }
        return null;
    }

    public Optional<Usuario> getUsuarioByNombre(String nombre) {
        return usuarioRepository.findByNombreUsr(nombre);
    }

    public void crearUsuarioDefault(){
        try {

                    Usuario defNorm = new Usuario();
        defNorm.setNombreUsr("JohnDoe");
        defNorm.setEmail("johndoe@gmail.com");
        defNorm.setReputacion(0);
        defNorm.setPasswd("paswd");
        defNorm.setTipo(TipoUsuario.USR);

        Usuario defAdmin = new Usuario();
        defAdmin.setNombreUsr("Admin");
        defAdmin.setEmail("admin@gmail.com");
        defAdmin.setReputacion(99);
        defAdmin.setPasswd("coolassadmin");
        defAdmin.setTipo(TipoUsuario.ADMIN);

        saveUsuario(defAdmin);
        saveUsuario(defNorm);

        } catch(Exception e) {
            System.out.println("Los usuarios ya estaban creados.");
        }

    }
}
