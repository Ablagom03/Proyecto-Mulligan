package com.muligan.cartas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("comentarioId")
    private Long comentarioId;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoComentario tipo;

    @ManyToOne
    @JoinColumn(name = "usr_id_comprador", nullable = false)
    @JsonIgnoreProperties({"comentariosDejaos", "comentariosRecibidos", "ofertas", "passwd"})
    private Usuario usuarioComprador;

    @ManyToOne
    @JoinColumn(name = "usr_id_vendedor", nullable = false)
    @JsonIgnoreProperties({"comentariosDejaos", "comentariosRecibidos", "ofertas", "passwd"})
    private Usuario usuarioVendedor;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    @JsonIgnoreProperties("comentarios")
    private Inventario inventario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
