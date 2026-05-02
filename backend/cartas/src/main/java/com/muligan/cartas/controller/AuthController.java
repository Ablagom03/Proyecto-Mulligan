package com.muligan.cartas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.muligan.cartas.dto.PeticionLogin;
import com.muligan.cartas.model.Usuario;
import com.muligan.cartas.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UsuarioService usuarioService;
  private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

  public AuthController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;

  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody PeticionLogin peticion,
      HttpServletRequest request,
      HttpServletResponse response) {

    Usuario usuario = usuarioService.autenticar(peticion.getEmail(), peticion.getPasswd());

    if (usuario != null) {
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
          usuario, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(auth);
      SecurityContextHolder.setContext(context);

      HttpSession session = request.getSession(true);
      session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
      securityContextRepository.saveContext(context, request, response);

      return ResponseEntity.ok(usuario);
    }

    return ResponseEntity.status(401).body("Credenciales incorrectas");
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(Authentication auth) {
    if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Usuario) {
      return ResponseEntity.ok((Usuario) auth.getPrincipal());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return ResponseEntity.ok().build();
  }

}
