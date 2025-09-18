package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GolesJugadorResponse {

    private Long jugadorId;
    private String nombreJugador;
    private String apellidoJugador;
    private Integer cantidadGoles;
}