package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorDto;

import java.util.List;

public interface GolesJugadorUseCase {

    List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId);

    void procesarGolesJugadores(Long enfrentamientoId,
                                Long equipoLocalId,
                                Long equipoVisitanteId,
                                List<GolesJugadorDto> golesJugadoresLocal,
                                List<GolesJugadorDto> golesJugadoresVisitante,
                                List<GolesJugador> golesExistentesAntes);

}
