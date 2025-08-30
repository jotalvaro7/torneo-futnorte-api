package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearEnfrentamientoRequest {
    
    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;
    
    @NotNull(message = "El equipo local es obligatorio")
    private Long equipoLocalId;
    
    @NotNull(message = "El equipo visitante es obligatorio")
    private Long equipoVisitanteId;
    
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;
    
    @NotNull(message = "La cancha es obligatoria")
    private String cancha;
}