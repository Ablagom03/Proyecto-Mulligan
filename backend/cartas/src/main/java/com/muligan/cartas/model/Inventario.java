package com.muligan.cartas.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Inventario 
 */

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usrid", referencedColumnName = "usrid")
    @JsonProperty("usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cardid", referencedColumnName = "cardid")
    @JsonProperty("carta")
    private Carta carta;

    @Column(name = "valor")
    private Integer valor;
    private String estado;
    private Integer copias;

}

