package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.Enfrentamiento;

public interface EstadisticasEquipoUseCase {

    void actualizarEstadisticas(Long equipoLocalId, Long equipoVisitanteId, Integer golesLocal, Integer golesVisitante);

    void revertirEstadisticasEquipo(Integer golesLocalPrevio, Integer golesVisitantePrevio, Long equipoLocalId, Long equipoVisitanteId);

}
