package com.muligan.cartas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.muligan.cartas.model.Imagen;
import com.muligan.cartas.service.ImagenService;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {
    
    private final ImagenService imagenService;
    
    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

     /**
     * Metodo: Get
     * URL: localhost:8080/
     * Proposito: Devuelve una imagen, para usarse en HTML y extraerlas de la bbdd
     * 
     * @return Imagen que tenga ese id
     */

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> verImagen(@PathVariable Long id) {
        Imagen img = imagenService.obtenerImagenById(id);

        if (img == null) {
            return ResponseEntity.notFound().build(); 
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(img.getData());
    }

}
