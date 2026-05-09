package com.muligan.cartas.controller;

import java.util.List;
import java.util.Map;

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
  /**
   * Autentica a un usuario y crea una sesión.
   *
   * @param peticion Datos de inicio de sesión.
   * @param request  Objeto de solicitud HTTP.
   * @param response Objeto de respuesta HTTP.
   * @return ResponseEntity con el resultado de la autenticación.
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody PeticionLogin peticion,
      HttpServletRequest request,
      HttpServletResponse response) {

    try {
      Usuario usuario = usuarioService.autenticar(peticion.getEmail(), peticion.getPasswd());

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          usuario, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authToken);
      SecurityContextHolder.setContext(context);

      HttpSession session = request.getSession(true);
      session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
      securityContextRepository.saveContext(context, request, response);

      return ResponseEntity.ok(usuario);

    } catch (RuntimeException e) {
    String mensaje = e.getMessage();
    
    if ("USUARIO_NO_ENCONTRADO".equals(mensaje)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", "No existe ninguna cuenta con ese correo."));
    } 
    
    if ("PASSWORD_INCORRECTA".equals(mensaje)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "La contraseña es incorrecta. Inténtalo de nuevo."));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", "Error al iniciar sesión."));
  }
}
  /**
   * Obtiene la información del usuario actualmente autenticado.
   *
   * @param auth Objeto de autenticación proporcionado por Spring Security.
   * @return ResponseEntity con la información del usuario o un error si no está autenticado.
   */
  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(Authentication auth) {
    if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Usuario) {
      return ResponseEntity.ok((Usuario) auth.getPrincipal());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
  /**
   * Cierra la sesión del usuario actual.
   *
   * @param request Objeto de solicitud HTTP.
   * @return ResponseEntity indicando que la sesión ha sido cerrada.
   */
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
