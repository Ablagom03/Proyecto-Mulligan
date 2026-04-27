package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Carta base
 */
@Entity
@Table(name = "carta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("cardid")
    private Long cardId;
    
    @Column(name = "nombrecard")
    @JsonProperty("nombrecard")
    private String nombreCard;
    private String descripcion;
    private String coleccion;
    private String empresa;

    @OneToOne
    @JoinColumn(name = "idimg")
    @JsonManagedReference
    private Imagen imagen; 
}
