package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @JsonProperty("usrId")
    private Long usrId;

    @Column(name = "nombre_usr")
    @JsonProperty("nombre_usr")
    private String nombreUsr;
    private String email;
    @JsonIgnore
    private String passwd;
}