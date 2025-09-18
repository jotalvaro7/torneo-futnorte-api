package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnfrentamientoResponse {
    
    private Long id;
    private Long torneoId;
    private String equipoLocal;
    private String equipoVisitante;
    private LocalDateTime fechaHora;
    private String cancha;
    private EstadoEnfrentamiento estado;
    private Integer golesLocal;
    private Integer golesVisitante;
    private List<GolesJugadorResponse> golesJugadoresLocal;
    private List<GolesJugadorResponse> golesJugadoresVisitante;
}