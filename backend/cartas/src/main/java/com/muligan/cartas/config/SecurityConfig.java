package com.muligan.cartas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // @Bean(name = "myAppCorsSource")
    // public CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration config = new CorsConfiguration();
    //
    // config.setAllowedOrigins(List.of("http://localhost:4200"));
    // config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // config.setAllowedHeaders(List.of("*"));
    // config.setAllowCredentials(true);
    // config.setExposedHeaders(List.of("Set-Cookie",
    // "Access-Control-Allow-Credentials"));
    //
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", config);
    // return source;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                .securityContext(context -> context
                        .securityContextRepository(new HttpSessionSecurityContextRepository()))

                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("/carta/**").permitAll()
                        .requestMatchers("/imagenes/**").permitAll()
                        .requestMatchers("/empresa/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/precios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/inventario/usuario/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/inventario/carta/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comentario/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ofertas/**").authenticated()

                        .anyRequest().authenticated())

                .exceptionHandling(ex -> {
                })
                // .authenticationEntryPoint((request, response, authException) -> {

                // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                // })

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        return http.build();
    }

    @SuppressWarnings("deprecation")
    @Bean

    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("SHA-512");
    }
}