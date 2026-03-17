package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Carta base
 */
@Entity
@Table(name = "carta")
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardid")
    @JsonProperty("cardid")
    private Long Cardid;
    
    @Column(name = "nombrecard")
    @JsonProperty("nombrecard")
    private String nombreCard;
    private String descripcion;
    private String coleccion;
    private String empresa;

    public Carta() {
    }

    public Carta(Long cardid, String nombreCard, String descripcion, String coleccion, String empresa) {
        Cardid = cardid;
        this.nombreCard = nombreCard;
        this.descripcion = descripcion;
        this.coleccion = coleccion;
        this.empresa = empresa;
    }

    public Long getCardid() {
        return this.Cardid;
    }

    public void setCardid(Long Cardid) {
        this.Cardid = Cardid;
    }

    public String getNombreCard() {
        return this.nombreCard;
    }

    public void setNombreCard(String nombreCard) {
        this.nombreCard = nombreCard;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColeccion() {
        return this.coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }

    public String getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Carta Cardid(Long Cardid) {
        setCardid(Cardid);
        return this;
    }

    public Carta nombreCard(String nombreCard) {
        setNombreCard(nombreCard);
        return this;
    }

    public Carta descripcion(String descripcion) {
        setDescripcion(descripcion);
        return this;
    }

    public Carta coleccion(String coleccion) {
        setColeccion(coleccion);
        return this;
    }

    public Carta empresa(String empresa) {
        setEmpresa(empresa);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Carta)) {
            return false;
        }
        Carta Carta = (Carta) o;
        return Objects.equals(Cardid, Carta.Cardid) && Objects.equals(nombreCard, Carta.nombreCard)
                && Objects.equals(descripcion, Carta.descripcion) && Objects.equals(coleccion, Carta.coleccion)
                && Objects.equals(empresa, Carta.empresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Cardid, nombreCard, descripcion, coleccion, empresa);
    }

    @Override
    public String toString() {
        return "{" +
                " Cardid='" + getCardid() + "'" +
                ", nombreCard='" + getNombreCard() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", coleccion='" + getColeccion() + "'" +
                ", empresa='" + getEmpresa() + "'" +
                "}";
    }

}
