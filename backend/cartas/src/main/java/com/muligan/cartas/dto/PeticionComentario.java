package com.muligan.cartas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PeticionComentario {
    
    @NotNull(message = "El ID de la oferta es requerido")
    private Long inventarioId;
    
    @NotBlank(message = "El comentario no puede estar vacío")
    private String texto;
    
    @NotBlank(message = "El tipo de comentario es requerido")
    private String tipo;

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
