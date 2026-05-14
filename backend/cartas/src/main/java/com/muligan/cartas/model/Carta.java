package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "El nombre de la carta es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre de la carta debe tener entre 1 y 100 caracteres")
    private String nombreCard;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 300, message = "La descripción debe tener entre 5 y 300 caracteres")
    private String descripcion;

    @NotBlank(message = "La colección es obligatoria")
    @Size(min = 1, max = 50, message = "La colección debe tener entre 1 y 50 caracteres")
    private String coleccion;

    @NotNull(message = "La empresa es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "empresa")
    private Empresa empresa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idimg", nullable = true)
    @JsonManagedReference
    private Imagen imagen;
}