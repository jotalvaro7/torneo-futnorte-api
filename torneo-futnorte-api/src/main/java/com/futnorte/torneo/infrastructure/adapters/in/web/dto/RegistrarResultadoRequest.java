package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarResultadoRequest {
    
    @NotNull(message = "Los goles del equipo local son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesLocal;
    
    @NotNull(message = "Los goles del equipo visitante son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesVisitante;
}