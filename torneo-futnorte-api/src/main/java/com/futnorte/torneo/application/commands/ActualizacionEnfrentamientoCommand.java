package com.futnorte.torneo.application.commands;

import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorDto;

import java.time.LocalDateTime;
import java.util.List;

public record ActualizacionEnfrentamientoCommand(
        Long enfrentamientoId,
        LocalDateTime fechaHora,
        String cancha,
        EstadoEnfrentamiento estadoEnfrentamiento,
        Integer golesLocal,
        Integer golesVisitante,
        List<GolesJugadorDto> golesJugadoresLocal,
        List<GolesJugadorDto> golesJugadoresVisitante) {

    public boolean esPartidoFinalizado() {
        return estadoEnfrentamiento == EstadoEnfrentamiento.FINALIZADO && golesLocal != null && golesVisitante != null;
    }

    public boolean tieneGolesJugadores() {
        return (golesJugadoresLocal != null && !golesJugadoresLocal.isEmpty()) || (golesJugadoresVisitante != null && !golesJugadoresVisitante.isEmpty());
    }

}
