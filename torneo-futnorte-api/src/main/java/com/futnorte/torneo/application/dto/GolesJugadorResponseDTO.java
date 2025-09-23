package com.futnorte.torneo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GolesJugadorResponseDTO {

    private Long jugadorId;
    private String nombreJugador;
    private String apellidoJugador;
    private Integer cantidadGoles;
}