package com.muligan.cartas.dto;

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
    private String valor;
    private String estado;
    private Integer copias;
}
