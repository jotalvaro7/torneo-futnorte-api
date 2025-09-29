package com.futnorte.torneo.application.services;

import com.futnorte.torneo.application.dto.GolesJugadorApplicationDTO;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.exceptions.ValidationException;
import com.futnorte.torneo.domain.ports.in.GolesJugadorUseCase;
import com.futnorte.torneo.domain.ports.out.GolesJugadorRepositoryPort;
import com.futnorte.torneo.domain.ports.out.JugadorRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GolesJugadorService implements GolesJugadorUseCase {

    private final GolesJugadorRepositoryPort golesJugadorRepositoryPort;
    private final JugadorRepositoryPort jugadorRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId) {
        log.debug("Obteniendo goles para enfrentamiento id={}", enfrentamientoId);
        return golesJugadorRepositoryPort.findByEnfrentamientoId(enfrentamientoId);
    }

    @Override
    public void procesarGolesJugadores(Long enfrentamientoId,
                                     Long equipoLocalId,
                                     Long equipoVisitanteId,
                                     List<GolesJugadorApplicationDTO> golesJugadoresLocal,
                                     List<GolesJugadorApplicationDTO> golesJugadoresVisitante,
                                     List<GolesJugador> golesExistentes) {
        log.debug("Procesando goles para enfrentamiento id={}", enfrentamientoId);

        // Revertir goles existentes
        revertirGolesExistentes(golesExistentes);

        // Eliminar registros anteriores
        if (!golesExistentes.isEmpty()) {
            eliminarGolesJugadoresPorEnfrentamiento(enfrentamientoId);
        }

        // Procesar nuevos goles
        List<GolesJugador> nuevosGoles = new ArrayList<>();
        procesarGolesEquipo(enfrentamientoId, equipoLocalId, golesJugadoresLocal, nuevosGoles);
        procesarGolesEquipo(enfrentamientoId, equipoVisitanteId, golesJugadoresVisitante, nuevosGoles);

        if (!nuevosGoles.isEmpty()) {
            golesJugadorRepositoryPort.saveAll(nuevosGoles);
        }
    }

    @Override
    public void eliminarGolesJugadoresPorEnfrentamiento(Long enfrentamientoId) {
        log.debug("Eliminando goles para enfrentamiento id={}", enfrentamientoId);
        // Primero obtenemos los goles existentes para revertir estadísticas
        List<GolesJugador> golesExistentes = obtenerGolesJugadoresPorEnfrentamiento(enfrentamientoId);

        // Revertimos las estadísticas de los jugadores
        revertirGolesExistentes(golesExistentes);

        // Finalmente eliminamos los registros
        golesJugadorRepositoryPort.deleteByEnfrentamientoId(enfrentamientoId);
        log.debug("Goles eliminados correctamente para enfrentamiento id={}", enfrentamientoId);
    }

    private void revertirGolesExistentes(List<GolesJugador> golesExistentes) {
        for (GolesJugador gol : golesExistentes) {
            Jugador jugador = buscarJugador(gol.getJugadorId());
            jugador.revertirGoles(gol.getCantidadGoles());
            jugadorRepositoryPort.guardar(jugador);
        }
    }

    private void procesarGolesEquipo(Long enfrentamientoId,
                                   Long equipoId,
                                   List<GolesJugadorApplicationDTO> golesJugadores,
                                   List<GolesJugador> nuevosGoles) {
        if (golesJugadores == null || golesJugadores.isEmpty()) {
            return;
        }

        for (GolesJugadorApplicationDTO gol : golesJugadores) {
            Jugador jugador = buscarJugador(gol.jugadorId());
            validarJugadorEquipo(jugador, equipoId);
            jugador.anotarGoles(gol.cantidadGoles());
            jugadorRepositoryPort.guardar(jugador);

            nuevosGoles.add(new GolesJugador(enfrentamientoId, gol.jugadorId(), gol.cantidadGoles()));
        }
    }

    private Jugador buscarJugador(Long jugadorId) {
        return jugadorRepositoryPort.buscarPorId(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador", jugadorId));
    }

    private void validarJugadorEquipo(Jugador jugador, Long equipoId) {
        if (!jugador.getEquipoId().equals(equipoId)) {
            throw new ValidationException("jugador",
                "El jugador no pertenece al equipo del enfrentamiento");
        }
    }
}