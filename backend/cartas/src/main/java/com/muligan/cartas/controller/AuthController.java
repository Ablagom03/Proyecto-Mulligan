package com.muligan.cartas.controller;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.muligan.cartas.dto.PeticionLogin;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.service.UsuarioService;
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UsuarioService usuarioService;

  public AuthController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody PeticionLogin request, HttpServletResponse response) {
    Usuario usuario = usuarioService.autenticar(request.getEmail(), request.getPasswd());
    if (usuario == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Cookie cookie = new Cookie("SESSIONID", usuario.getUsrId().toString());
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // Cambiar en caso de usar HTTPS
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60);
    response.addCookie(cookie);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/me")
  public ResponseEntity<Optional<Usuario>> getCurrentUser(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("SESSIONID".equals(cookie.getName())) {
          try {
            Long usrId = Long.parseLong(cookie.getValue());
            Optional<Usuario> usuario = usuarioService.getUsuarioById(usrId);
            if (usuario != null) {
              return ResponseEntity.ok(usuario);
            }
          } catch (NumberFormatException e) {
            
          }
        }
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("SESSIONID", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
    return ResponseEntity.ok().build();
  }
}

