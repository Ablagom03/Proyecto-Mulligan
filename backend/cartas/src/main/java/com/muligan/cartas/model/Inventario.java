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
import java.util.Objects;


/**
 * Inventario 
 */

@Entity
@Table(name = "inventario")
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

    
    public Inventario() {}


    public Inventario(Long id, Usuario usuario, Carta carta, Integer valor, String estado, Integer copias) {
        this.id = id;
        this.usuario = usuario;
        this.carta = carta;
        this.valor = valor;
        this.estado = estado;
        this.copias = copias;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Carta getCarta() {
        return this.carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Integer getValor() {
        return this.valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCopias() {
        return this.copias;
    }

    public void setCopias(Integer copias) {
        this.copias = copias;
    }

    public Inventario id(Long id) {
        setId(id);
        return this;
    }

    public Inventario usuario(Usuario usuario) {
        setUsuario(usuario);
        return this;
    }

    public Inventario carta(Carta carta) {
        setCarta(carta);
        return this;
    }

    public Inventario Valor(Integer Valor) {
        setValor(Valor);
        return this;
    }

    public Inventario estado(String estado) {
        setEstado(estado);
        return this;
    }

    public Inventario copias(Integer copias) {
        setCopias(copias);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Inventario)) {
            return false;
        }
        Inventario inventario = (Inventario) o;
        return Objects.equals(id, inventario.id) && Objects.equals(usuario, inventario.usuario) && Objects.equals(carta, inventario.carta) && Objects.equals(valor, inventario.valor) && Objects.equals(estado, inventario.estado) && Objects.equals(copias, inventario.copias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, carta, valor, estado, copias);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", carta='" + getCarta() + "'" +
            ", Valor='" + getValor() + "'" +
            ", estado='" + getEstado() + "'" +
            ", copias='" + getCopias() + "'" +
            "}";
    }

}

