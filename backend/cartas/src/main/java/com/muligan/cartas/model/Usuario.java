package com.muligan.cartas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "usuario")
public class Usuario {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "usrId")
   @JsonProperty("usrId")
    private Long usrId;

    @Column(name = "nombre_usr")
    @JsonProperty("nombre_usr")
    private String nombreUsr;
    private String email;
    @JsonIgnore
    private String passwd;

    public Usuario(){

    }

    public Usuario(Long usrId, String nombreUsr, String email, String passwd){
        this.usrId = usrId;
        this.nombreUsr = nombreUsr;
        this.email = email;
        this.passwd = passwd;
    }

    public Long getUsrId(){
        return this.usrId;
    }

    public void setUsrId(Long usrId){
        this.usrId = usrId;
    }

    public String getNombreUsr(){
        return this.nombreUsr;
    }

    public void setNombreUsr(String nombreUsr){
        this.nombreUsr = nombreUsr;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPasswd(){
        return this.passwd;
    }

    public void setPasswd(String passwd){
        this.passwd = passwd;
    }
}