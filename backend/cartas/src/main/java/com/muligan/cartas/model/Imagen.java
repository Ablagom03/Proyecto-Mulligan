package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase para las imagenes para importarlas
 */

@Entity
@Table(name = "imagenes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idimg")
    private Long idImg;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "data", columnDefinition = "BYTEA")
    private byte[] data;
}