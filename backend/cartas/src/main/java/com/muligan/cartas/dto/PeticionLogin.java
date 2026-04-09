package com.muligan.cartas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeticionLogin {
  private String email;
  private String passwd;
}

