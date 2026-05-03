package com.muligan.cartas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioUpdateDTO {
private String tipo;
    private Double valor;
    private String estado;
    private Integer copias;
    private String nombreCard;
}
