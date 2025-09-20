package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.ports.in.EstadisticasEquipoUseCase;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EstadisticasEquipoService implements EstadisticasEquipoUseCase {

    private final EquipoRepositoryPort equipoRepository;

    @Override
    public void actualizarEstadisticas(Long equipoLocalId, Long equipoVisitanteId, Integer golesLocal, Integer golesVisitante) {
        Equipo equipoLocal = obtenerEquipo(equipoLocalId);
        Equipo equipoVisitante = obtenerEquipo(equipoVisitanteId);

        ResultadoPartido resultado = determinarResultado(golesLocal, golesVisitante);

        aplicarResultadoAEquipos(equipoLocal, equipoVisitante, golesLocal, golesVisitante, resultado);

        guardarEquipos(equipoLocal, equipoVisitante);
    }


    private ResultadoPartido determinarResultado(int golesLocal, int golesVisitante) {
        if (golesLocal == golesVisitante) {
            return ResultadoPartido.EMPATE;
        } else if (golesLocal > golesVisitante) {
            return ResultadoPartido.VICTORIA_LOCAL;
        } else {
            return ResultadoPartido.VICTORIA_VISITANTE;
        }
    }

    private void aplicarResultadoAEquipos(Equipo equipoLocal, Equipo equipoVisitante,
                                          int golesLocal, int golesVisitante,
                                          ResultadoPartido resultado) {
        switch (resultado) {
            case EMPATE:
                equipoLocal.registrarEmpate(golesLocal, golesVisitante);
                equipoVisitante.registrarEmpate(golesVisitante, golesLocal);
                log.debug("Empate registrado: {}-{}", golesLocal, golesVisitante);
                break;
            case VICTORIA_LOCAL:
                equipoLocal.registrarVictoria(golesLocal, golesVisitante);
                equipoVisitante.registrarDerrota(golesVisitante, golesLocal);
                log.debug("Victoria local {}-{}", golesLocal, golesVisitante);
                break;
            case VICTORIA_VISITANTE:
                equipoVisitante.registrarVictoria(golesVisitante, golesLocal);
                equipoLocal.registrarDerrota(golesLocal, golesVisitante);
                log.debug("Victoria visitante {}-{}", golesVisitante, golesLocal);
                break;
        }
    }

    private void guardarEquipos(Equipo equipoLocal, Equipo equipoVisitante) {
        equipoRepository.guardar(equipoLocal);
        equipoRepository.guardar(equipoVisitante);
    }

    private Equipo obtenerEquipo(Long equipoId) {
        return equipoRepository.buscarPorId(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo", equipoId));
    }

    private enum ResultadoPartido {
        EMPATE, VICTORIA_LOCAL, VICTORIA_VISITANTE
    }
}

