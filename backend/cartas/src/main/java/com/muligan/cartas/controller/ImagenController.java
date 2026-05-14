package com.muligan.cartas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.muligan.cartas.model.Imagen;
import com.muligan.cartas.service.ImagenService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    /**
     * Método Get
     * URL: localhost:8080/imagen/{nombre}
     * Propósito: Devuelve una imagen, via Nombre, para mayor facilidad de
     * implementación en HTML
     * 
     * @return Imagen con ese nombre
     */

    @GetMapping("/{nombre}")
    public ResponseEntity<byte[]> verImagenNombre(@PathVariable String nombre) {
        Imagen img = imagenService.obtenerImagenByNombre(nombre);

        if (img == null) {
            System.out.println("Imagen no encontrada con ese nombre: " + nombre);
            return ResponseEntity.notFound().build();
        }

        System.out.println("Imagen encontrada: " + img.getNombre()
                + " | bytes: " + (img.getData() != null ? img.getData().length : "NULL"));

        if (img.getData() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(img.getData());
    }

    /**
     * Método Post
     * URL: localhost:8080/imagen/
     * Propósito: Inserta una imagen en la bbdd
     * 
     * @param imagen a insertar
     * @return imagen insertada
     */

    @PostMapping
    public ResponseEntity<Imagen> insertarImagen(@RequestBody Imagen imagen) {
        Imagen imagenInsertada = imagenService.guardarImagen(imagen);
        return ResponseEntity.ok(imagenInsertada);
    }

    /**
     * Método Put
     * URL: localhost:8080/imagen/{nombre}
     * Propósito: Actualiza una imagen en la bbdd
     * 
     * @param imagen a actualizar
     * @return imagen actualizada
     */
    @PutMapping("/{nombre}")
    public ResponseEntity<Imagen> actualizarImagen(
            @PathVariable String nombre,
            @RequestParam("archivo") MultipartFile archivo) {

        try {
            Imagen imagenActualizada = imagenService.updateImagen(nombre, archivo.getBytes());

            if (imagenActualizada != null) {
                return ResponseEntity.ok(imagenActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
