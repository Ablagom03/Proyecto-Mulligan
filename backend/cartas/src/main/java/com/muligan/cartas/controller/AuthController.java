package com.muligan.cartas.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.muligan.cartas.dto.LoginRequest;
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
  public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
    Usuario usuario = usuarioService.autenticar(request.getEmail(), request.getPasswd());
    if (usuario == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Cookie cookie = new Cookie("SESSIONID", usuario.getUsrId().toString());
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // true in production with HTTPS
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60);
    response.addCookie(cookie);

    return ResponseEntity.ok().build();
  }
}

