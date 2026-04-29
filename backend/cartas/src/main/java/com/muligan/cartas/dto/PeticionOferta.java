package com.muligan.cartas.dto;

import com.muligan.cartas.model.TipoOferta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeticionOferta {
    private String nombreCard;
    private Double valor;
    private String estado;
    private Integer copias;
    private TipoOferta tipo;
}
