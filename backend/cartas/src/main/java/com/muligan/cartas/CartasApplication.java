package com.muligan.cartas;

import com.muligan.cartas.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.muligan.cartas")

public class CartasApplication implements CommandLineRunner{
	@Autowired
	private UsuarioService usuarioService;

    public static void main(String[] args) {
		SpringApplication.run(CartasApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        usuarioService.crearUsuarioDefault();
    }

}
