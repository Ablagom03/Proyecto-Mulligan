package com.muligan.cartas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Usuario usuarioComprador;

    @ManyToOne
    @JoinColumn(name = "usr_id_vendedor", nullable = false)
    private Usuario usuarioVendedor;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
