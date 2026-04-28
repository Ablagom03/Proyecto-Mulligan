package com.muligan.cartas.controller;

import java.util.List;

import com.muligan.cartas.model.Empresa;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    /**
     * Metodo: GET
     * URL: localhost:8080/empresa/
     * Proposito: Devolver todas las empresas
     * 
     * @return lista de empresas
     */
    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpresas() {
        List<Empresa> empresas = List.of(Empresa.values());
        return ResponseEntity.ok(empresas);
    }    
}
