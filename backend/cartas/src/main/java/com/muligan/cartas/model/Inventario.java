package com.muligan.cartas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * Inventario 
 */

@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @Column(name = "cardid")
    @JsonProperty("cardid")
    @OneToMany(mappedBy = "cardid")
    private Integer Cardid;
    @OneToMany(mappedBy = "userid")
    private Integer Userid;

    @Column(name = "valor")
    @JsonProperty("valor")
    private Integer Valor;
    private String estado;
    private Integer copias;

    
    public Inventario() {}
}

