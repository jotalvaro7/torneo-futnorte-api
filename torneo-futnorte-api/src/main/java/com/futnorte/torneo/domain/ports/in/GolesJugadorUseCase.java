package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.application.dto.GolesJugadorApplicationDTO;
import com.futnorte.torneo.domain.entities.GolesJugador;

import java.util.List;

public interface GolesJugadorUseCase {

    List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId);

    void procesarGolesJugadores(Long enfrentamientoId,
                                Long equipoLocalId,
                                Long equipoVisitanteId,
                                List<GolesJugadorApplicationDTO> golesJugadoresLocal,
                                List<GolesJugadorApplicationDTO> golesJugadoresVisitante,
                                List<GolesJugador> golesExistentesAntes);
                                
    void eliminarGolesJugadoresPorEnfrentamiento(Long enfrentamientoId);
}
