package com.muligan.cartas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Inventario
 */

@Entity
@Table(name = "inventario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invid")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usrid")
    @JsonIgnoreProperties("ofertas")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cardid")
    private Carta carta;

    @Column
    private Double valor;
    private String estado;
    private Integer copias;

    @Enumerated(EnumType.STRING)
    private TipoOferta tipo;

    @JsonProperty("usrId")
    public Long getUsrId() {
        return usuario != null ? usuario.getUsrId() : null;
    }

    @JsonProperty("cardId")
    public Long getCardId() {
        return carta != null ? carta.getCardId() : null;
    }

    public Double getValor() {
        return Math.round(valor * 100.0) / 100.0;
    }
}
