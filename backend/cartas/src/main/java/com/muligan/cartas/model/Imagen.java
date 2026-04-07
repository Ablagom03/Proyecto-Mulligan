package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para las imagenes para importarlas
 */

@Entity
@Table(name = "imagenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImg;

    private String nombre;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] data;
}
