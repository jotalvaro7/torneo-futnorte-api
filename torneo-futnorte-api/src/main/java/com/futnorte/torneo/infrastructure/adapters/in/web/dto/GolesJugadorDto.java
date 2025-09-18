package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GolesJugadorDto {

    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La cantidad de goles es obligatoria")
    @Min(value = 1, message = "La cantidad de goles debe ser mayor a 0")
    private Integer cantidadGoles;
}