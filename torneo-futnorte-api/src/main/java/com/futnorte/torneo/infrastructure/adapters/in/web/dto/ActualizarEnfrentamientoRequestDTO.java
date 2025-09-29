package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEnfrentamientoRequestDTO {

    private LocalDateTime fechaHora;
    private String cancha;
    private EstadoEnfrentamiento estado;

    @Min(value = 0, message = "Los goles locales no pueden ser negativos")
    private Integer golesLocal;

    @Min(value = 0, message = "Los goles visitantes no pueden ser negativos")
    private Integer golesVisitante;

    @Valid
    private List<GolesJugadorDto> golesJugadoresLocal;

    @Valid
    private List<GolesJugadorDto> golesJugadoresVisitante;
}