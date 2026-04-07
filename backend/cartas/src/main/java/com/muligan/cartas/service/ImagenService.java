package com.muligan.cartas.service;

import org.springframework.stereotype.Service;

import com.muligan.cartas.model.Imagen;
import com.muligan.cartas.repository.ImagenRepository;

@Service
public class ImagenService {

    private final ImagenRepository imagenRepository;

    public ImagenService(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    public Imagen obtenerImagenById(Long id) {
        return imagenRepository.findById(id).orElse(null);
    }

    public Imagen obtenerImagenByNombre(String nombre) {
        return imagenRepository.findBynombreImagen(nombre);
    }

    public Imagen guardarImagen(Imagen img) {
        return imagenRepository.save(img);
    }
    
}
