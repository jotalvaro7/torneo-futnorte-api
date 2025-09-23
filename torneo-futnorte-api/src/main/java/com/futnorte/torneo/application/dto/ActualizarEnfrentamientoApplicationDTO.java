package com.futnorte.torneo.application.dto;


import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;

import java.time.LocalDateTime;
import java.util.List;

public record ActualizarEnfrentamientoApplicationDTO(Long enfrentamientoId, LocalDateTime fechaHora, String cancha,
                                                     EstadoEnfrentamiento estadoEnfrentamiento, Integer golesLocal,
                                                     Integer golesVisitante, List<GolesJugadorApplicationDTO> golesJugadoresLocal,
                                                     List<GolesJugadorApplicationDTO> golesJugadoresVisitante) {

    public boolean esPartidoFinalizado() {
        return estadoEnfrentamiento == EstadoEnfrentamiento.FINALIZADO;
    }

    public boolean tieneNuevosGolesElEnfrentamiento() {
        return (golesJugadoresLocal != null && !golesJugadoresLocal.isEmpty()) || (golesJugadoresVisitante != null && !golesJugadoresVisitante.isEmpty());
    }

}
