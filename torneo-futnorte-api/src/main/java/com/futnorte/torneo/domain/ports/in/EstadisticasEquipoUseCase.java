package com.futnorte.torneo.domain.ports.in;

public interface EstadisticasEquipoUseCase {

    void actualizarEstadisticas(Long equipoLocalId, Long equipoVisitanteId, Integer golesLocal, Integer golesVisitante);

}
