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


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class GolesJugadorService implements GolesJugadorUseCase {

    private final GolesJugadorRepositoryPort golesJugadorRepository;
    private final JugadorRepositoryPort jugadorRepository;


    @Transactional(readOnly = true)
    public List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId) {

        try {
            log.debug("Obteniendo goles existentes para enfrentamiento ID: {}", enfrentamientoId);

            // USAR EL CASO DE USO PARA OBTENER LOS GOLES
            List<GolesJugador> goles = golesJugadorRepository.findByEnfrentamientoId(enfrentamientoId);

            log.debug("Goles existentes encontrados: {}", goles.size());
            return goles;

        } catch (Exception e) {
            log.error("Error al obtener goles existentes para enfrentamiento ID {}: {}",
                    enfrentamientoId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public void procesarGolesJugadores(Long enfrentamientoId,
                                       Long equipoLocalId,
                                       Long equipoVisitanteId,
                                       List<GolesJugadorApplicationDTO> golesJugadoresLocal,
                                       List<GolesJugadorApplicationDTO> golesJugadoresVisitante,
                                       List<GolesJugador> golesExistentes) {
        log.debug("Procesando goles de jugadores para enfrentamiento ID: {}", enfrentamientoId);

        try {


            revertirYEliminarGolesExistentes(enfrentamientoId, golesExistentes);

            List<GolesJugador> nuevosGoles = crearNuevosGoles(enfrentamientoId,equipoLocalId, equipoVisitanteId, golesJugadoresLocal, golesJugadoresVisitante);

            persistirNuevosGoles(nuevosGoles);

        } catch (Exception e) {
            log.error("Error al procesar goles de jugadores: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar goles de jugadores: " + e.getMessage(), e);
        }

    }

    private List<GolesJugador> obtenerGolesExistentes(Long enfrentamientoId) {
        try {
            return golesJugadorRepository.findByEnfrentamientoId(enfrentamientoId);
        } catch (Exception e) {
            log.warn("Error al obtener goles existentes: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private void revertirYEliminarGolesExistentes(Long enfrentamientoId, List<GolesJugador> golesExistentes) {
        if (golesExistentes.isEmpty()) {
            return;
        }

        revertirGolesEnJugadores(golesExistentes);
        eliminarRegistrosGoles(enfrentamientoId);
    }

    private void revertirGolesEnJugadores(List<GolesJugador> golesExistentes) {
        log.debug("Revirtiendo {} goles existentes", golesExistentes.size());

        for (GolesJugador goles : golesExistentes) {
            try {
                Jugador jugador = obtenerJugador(goles.getJugadorId());
                jugador.revertirGoles(goles.getCantidadGoles());
                jugadorRepository.guardar(jugador);

                log.debug("Revertidos {} goles del jugador {}",
                        goles.getCantidadGoles(), jugador.getNombre());
            } catch (Exception e) {
                log.warn("Error al revertir goles para jugador ID {}: {}",
                        goles.getJugadorId(), e.getMessage());
            }
        }
    }

    private void eliminarRegistrosGoles(Long enfrentamientoId) {
        try {
            golesJugadorRepository.deleteByEnfrentamientoId(enfrentamientoId);
            log.debug("Eliminados registros de goles para enfrentamiento ID: {}", enfrentamientoId);
        } catch (Exception e) {
            log.warn("Error al eliminar registros de goles: {}", e.getMessage());
        }
    }

    private List<GolesJugador> crearNuevosGoles(Long enfrentamientoId,
                                                Long equipoLocalId,
                                                Long equipoVisitanteId,
                                                List<GolesJugadorApplicationDTO> golesLocal,
                                                List<GolesJugadorApplicationDTO> golesVisitante) {
        List<GolesJugador> nuevosGoles = new ArrayList<>();

        // Procesar goles del equipo local
        nuevosGoles.addAll(procesarGolesEquipo(enfrentamientoId, golesLocal, equipoLocalId));

        // Procesar goles del equipo visitante
        nuevosGoles.addAll(procesarGolesEquipo(enfrentamientoId, golesVisitante, equipoVisitanteId));

        return nuevosGoles;
    }

    private List<GolesJugador> procesarGolesEquipo(Long enfrentamientoId,
                                                   List<GolesJugadorApplicationDTO> golesJugadores,
                                                   Long equipoId) {
        if (golesJugadores == null || golesJugadores.isEmpty()) {
            return new ArrayList<>();
        }

        List<GolesJugador> golesEquipo = new ArrayList<>();

        for (GolesJugadorApplicationDTO golesDto : golesJugadores) {
            try {
                registrarGolesEnJugador(golesDto.jugadorId(),
                        golesDto.cantidadGoles(), equipoId);

                golesEquipo.add(new GolesJugador(enfrentamientoId,
                        golesDto.jugadorId(), golesDto.cantidadGoles()));

            } catch (Exception e) {
                log.warn("Error al procesar gol para jugador ID {}: {}",
                        golesDto.jugadorId(), e.getMessage());
            }
        }

        return golesEquipo;
    }

    private void registrarGolesEnJugador(Long jugadorId, int cantidadGoles, Long equipoEsperado) {
        Jugador jugador = obtenerJugador(jugadorId);

        if (!jugador.getEquipoId().equals(equipoEsperado)) {
            throw new ValidationException("jugador", "debe pertenecer al equipo correspondiente en el enfrentamiento");
        }

        jugador.anotarGoles(cantidadGoles);
        jugadorRepository.guardar(jugador);
    }

    private void persistirNuevosGoles(List<GolesJugador> nuevosGoles) {
        if (nuevosGoles.isEmpty()) {
            return;
        }

        log.debug("Guardando {} registros de goles", nuevosGoles.size());
        golesJugadorRepository.saveAll(nuevosGoles);
        log.debug("Goles persistidos exitosamente");
    }

    private Jugador obtenerJugador(Long jugadorId) {
        return jugadorRepository.buscarPorId(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador", jugadorId));
    }
}

