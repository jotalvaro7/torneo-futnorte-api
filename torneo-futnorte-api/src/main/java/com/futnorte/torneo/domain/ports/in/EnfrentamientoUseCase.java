package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EnfrentamientoUseCase {

    Enfrentamiento crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                     LocalDateTime fechaHora, String cancha);

    Enfrentamiento actualizarEnfrentamiento(Long enfrentamientoId, LocalDateTime fechaHora, String cancha,
                                          EstadoEnfrentamiento estado, Integer golesLocal, Integer golesVisitante,
                                          List<GolesJugadorDto> golesJugadoresLocal, List<GolesJugadorDto> golesJugadoresVisitante);


    Optional<Enfrentamiento> obtenerEnfrentamientoPorId(Long enfrentamientoId);

    List<Enfrentamiento> obtenerEnfrentamientosPorTorneo(Long torneoId);

    List<Enfrentamiento> obtenerEnfrentamientosPorEquipo(Long equipoId);

    List<Enfrentamiento> obtenerEnfrentamientosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    void eliminarEnfrentamiento(Long enfrentamientoId);

    List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId);
}