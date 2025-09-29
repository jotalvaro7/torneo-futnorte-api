package com.futnorte.torneo.application.services;

import com.futnorte.torneo.application.dto.ActualizarEnfrentamientoApplicationDTO;
import com.futnorte.torneo.application.dto.EnfrentamientoResponseDTO;
import com.futnorte.torneo.application.dto.GolesJugadorApplicationDTO;
import com.futnorte.torneo.application.dto.GolesJugadorResponseDTO;
import com.futnorte.torneo.application.mapper.EnfrentamientoApplicationMapper;
import com.futnorte.torneo.application.validators.EnfrentamientoValidator;
import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.domain.ports.in.EstadisticasEquipoUseCase;
import com.futnorte.torneo.domain.ports.in.GolesJugadorUseCase;
import com.futnorte.torneo.domain.ports.in.JugadorUseCase;
import com.futnorte.torneo.domain.ports.out.EnfrentamientoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class EnfrentamientoService implements EnfrentamientoUseCase {

    private final EnfrentamientoRepositoryPort enfrentamientoRepositoryPort;
    private final EnfrentamientoValidator enfrentamientoValidator;
    private final EstadisticasEquipoUseCase estadisticasEquipoUseCase;
    private final GolesJugadorUseCase golesJugadorUseCase;
    private final JugadorUseCase jugadorUseCase;
    private final EnfrentamientoApplicationMapper enfrentamientoApplicationMapper;

    private record GolesEquipo(List<GolesJugadorResponseDTO> golesLocal, List<GolesJugadorResponseDTO> golesVisitante) {
    }

    @Override
    public EnfrentamientoResponseDTO crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                                         LocalDateTime fechaHora, String cancha) {

        enfrentamientoValidator.validarCreacionEnfrentamiento(torneoId, equipoLocalId, equipoVisitanteId);
        Enfrentamiento enfrentamiento = new Enfrentamiento(torneoId, equipoLocalId, equipoVisitanteId, fechaHora, cancha);
        enfrentamiento.validarEnfrentamiento();

        log.info("Creando enfrentamiento torneoId={}, local={}, visitante={}", torneoId, equipoLocalId, equipoVisitanteId);

        Enfrentamiento enfrentamientoGuardado = enfrentamientoRepositoryPort.save(enfrentamiento);

        return enfrentamientoApplicationMapper.toEnfrentamientoResponseDTO(
                enfrentamientoGuardado,
                null,
                null);

    }

    @Override
    public EnfrentamientoResponseDTO actualizarEnfrentamiento(ActualizarEnfrentamientoApplicationDTO actualizarEnfrentamientoApplicationDTO) {

        Enfrentamiento enfrentamiento = buscarEnfrentamiento(actualizarEnfrentamientoApplicationDTO.enfrentamientoId());
        EstadoEnfrentamiento estadoPrevio = enfrentamiento.getEstado();

        revertirEstadisticasSiYaFinalizado(enfrentamiento, estadoPrevio);

        List<GolesJugador> golesExistentes = obtenerGolesExistentes(actualizarEnfrentamientoApplicationDTO.enfrentamientoId());

        aplicarActualizacionEnfrentamiento(actualizarEnfrentamientoApplicationDTO, enfrentamiento);

        if (actualizarEnfrentamientoApplicationDTO.esPartidoFinalizado()) {
            aplicarActualizacionEstadisticas(enfrentamiento);
        }

        Enfrentamiento enfrentamientoGuardado = enfrentamientoRepositoryPort.save(enfrentamiento);

        if (actualizarEnfrentamientoApplicationDTO.tieneNuevosGolesElEnfrentamiento()) {
            procesarGolesJugadoresEnfrentamiento(
                    enfrentamientoGuardado,
                    actualizarEnfrentamientoApplicationDTO.golesJugadoresLocal(),
                    actualizarEnfrentamientoApplicationDTO.golesJugadoresVisitante(),
                    golesExistentes
            );
        } else if (esOtroEstadoDiferenteAFinalizado(actualizarEnfrentamientoApplicationDTO, estadoPrevio)) {
            procesarGolesJugadoresEnfrentamiento(
                    enfrentamientoGuardado,
                    Collections.emptyList(),
                    Collections.emptyList(),
                    golesExistentes
            );
        }

        return enfrentamientoApplicationMapper.toEnfrentamientoResponseDTO(
                enfrentamientoGuardado,
                mapearGolesPorEquipo(enfrentamientoGuardado).getFirst(),
                mapearGolesPorEquipo(enfrentamientoGuardado).getSecond()
        );

    }

    private boolean esOtroEstadoDiferenteAFinalizado(ActualizarEnfrentamientoApplicationDTO actualizarEnfrentamientoApplicationDTO, EstadoEnfrentamiento estadoPrevio) {
        return estadoPrevio == EstadoEnfrentamiento.FINALIZADO && actualizarEnfrentamientoApplicationDTO.estadoEnfrentamiento() != EstadoEnfrentamiento.FINALIZADO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnfrentamientoResponseDTO> obtenerEnfrentamientoPorId(Long enfrentamientoId) {
        return enfrentamientoRepositoryPort.findById(enfrentamientoId)
                .map(enfrentamiento -> enfrentamientoApplicationMapper.toEnfrentamientoResponseDTO(
                        enfrentamiento,
                        mapearGolesPorEquipo(enfrentamiento).getFirst(),
                        mapearGolesPorEquipo(enfrentamiento).getSecond()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnfrentamientoResponseDTO> obtenerEnfrentamientosPorTorneo(Long torneoId) {
        return enfrentamientoRepositoryPort.findByTorneoId(torneoId)
                .stream()
                .map(enfrentamiento -> enfrentamientoApplicationMapper.toEnfrentamientoResponseDTO(
                        enfrentamiento,
                        mapearGolesPorEquipo(enfrentamiento).getFirst(),
                        mapearGolesPorEquipo(enfrentamiento).getSecond()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnfrentamientoResponseDTO> obtenerEnfrentamientosPorEquipo(Long equipoId) {
        return enfrentamientoRepositoryPort.findByEquipoLocalIdOrEquipoVisitanteId(equipoId, equipoId)
                .stream()
                .map(enfrentamiento -> enfrentamientoApplicationMapper.toEnfrentamientoResponseDTO(
                        enfrentamiento,
                        mapearGolesPorEquipo(enfrentamiento).getFirst(),
                        mapearGolesPorEquipo(enfrentamiento).getSecond()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnfrentamientoResponseDTO> obtenerEnfrentamientosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return enfrentamientoRepositoryPort.findByFechaHoraBetween(fechaInicio, fechaFin)
                .stream()
                .map(enfrentamiento -> enfrentamientoApplicationMapper.toEnfrentamientoResponseDTO(
                        enfrentamiento,
                        mapearGolesPorEquipo(enfrentamiento).getFirst(),
                        mapearGolesPorEquipo(enfrentamiento).getSecond()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarEnfrentamiento(Long enfrentamientoId) {
        log.info("Iniciando proceso de eliminación del enfrentamiento ID: {}", enfrentamientoId);

        Enfrentamiento enfrentamiento = buscarEnfrentamiento(enfrentamientoId);

        // Si está finalizado, revertir las estadísticas
        if (enfrentamiento.getEstado() == EstadoEnfrentamiento.FINALIZADO) {
            log.info("Revirtiendo estadísticas del enfrentamiento finalizado ID: {}", enfrentamientoId);
            aplicarRevertirEstadisticasEquipos(enfrentamiento);
        }

        // Eliminar los goles asociados
        log.debug("Eliminando goles asociados al enfrentamiento ID: {}", enfrentamientoId);
        golesJugadorUseCase.eliminarGolesJugadoresPorEnfrentamiento(enfrentamientoId);

        // Eliminar el enfrentamiento
        log.info("Eliminando enfrentamiento ID: {}", enfrentamientoId);
        enfrentamientoRepositoryPort.deleteById(enfrentamientoId);

        log.info("Enfrentamiento ID: {} eliminado exitosamente", enfrentamientoId);
    }

    private Pair<List<GolesJugadorResponseDTO>, List<GolesJugadorResponseDTO>> mapearGolesPorEquipo(Enfrentamiento enfrentamiento) {
        List<GolesJugador> golesJugadores = obtenerGolesJugadores(enfrentamiento.getId());
        if (golesJugadores.isEmpty()) {
            return Pair.of(Collections.emptyList(), Collections.emptyList());
        }

        Map<Long, Jugador> jugadoresMap = obtenerMapaJugadores(golesJugadores);
        GolesEquipo golesEquipo = separarGolesPorEquipo(golesJugadores, jugadoresMap, enfrentamiento);

        return Pair.of(golesEquipo.golesLocal(), golesEquipo.golesVisitante());
    }

    private List<GolesJugador> obtenerGolesJugadores(Long enfrentamientoId) {
        log.debug("Obteniendo goles para enfrentamiento id={}", enfrentamientoId);
        return golesJugadorUseCase.obtenerGolesJugadoresPorEnfrentamiento(enfrentamientoId);
    }

    private Map<Long, Jugador> obtenerMapaJugadores(List<GolesJugador> golesJugadores) {
        List<Long> jugadorIds = golesJugadores.stream()
                .map(GolesJugador::getJugadorId)
                .toList();

        List<Jugador> jugadores = jugadorUseCase.buscarJugadoresPorIds(jugadorIds);
        log.debug("Encontrados {} jugadores de {} solicitados", jugadores.size(), jugadorIds.size());

        return jugadores.stream()
                .collect(Collectors.toMap(Jugador::getId, Function.identity()));
    }

    private GolesEquipo separarGolesPorEquipo(List<GolesJugador> golesJugadores,
                                              Map<Long, Jugador> jugadoresMap,
                                              Enfrentamiento enfrentamiento) {
        List<GolesJugadorResponseDTO> golesLocal = new ArrayList<>();
        List<GolesJugadorResponseDTO> golesVisitante = new ArrayList<>();

        for (GolesJugador gol : golesJugadores) {
            Jugador jugador = jugadoresMap.get(gol.getJugadorId());
            if (jugador == null) {
                log.warn("Jugador con id {} no encontrado para goles", gol.getJugadorId());
                continue;
            }

            GolesJugadorResponseDTO dto = crearGolesJugadorDTO(jugador, gol);
            asignarGolesAEquipo(dto, jugador, enfrentamiento, golesLocal, golesVisitante);
        }

        return new GolesEquipo(golesLocal, golesVisitante);
    }

    private GolesJugadorResponseDTO crearGolesJugadorDTO(Jugador jugador, GolesJugador gol) {
        return new GolesJugadorResponseDTO(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getApellido(),
                gol.getCantidadGoles()
        );
    }

    private void asignarGolesAEquipo(GolesJugadorResponseDTO dto,
                                     Jugador jugador,
                                     Enfrentamiento enfrentamiento,
                                     List<GolesJugadorResponseDTO> golesLocal,
                                     List<GolesJugadorResponseDTO> golesVisitante) {
        if (jugador.getEquipoId().equals(enfrentamiento.getEquipoLocalId())) {
            golesLocal.add(dto);
        } else if (jugador.getEquipoId().equals(enfrentamiento.getEquipoVisitanteId())) {
            golesVisitante.add(dto);
        } else {
            log.warn("Jugador {} no pertenece al enfrentamiento {}", jugador.getId(), enfrentamiento.getId());
        }
    }

    private void revertirEstadisticasSiYaFinalizado(Enfrentamiento enfrentamiento, EstadoEnfrentamiento estadoPrevio) {
        if (estadoPrevio == EstadoEnfrentamiento.FINALIZADO) {
            log.info("Revirtiendo estadísticas para enfrentamiento {} (Local: {}, Visitante: {})",
                    enfrentamiento.getId(),
                    enfrentamiento.getEquipoLocalId(),
                    enfrentamiento.getEquipoVisitanteId());
            aplicarRevertirEstadisticasEquipos(enfrentamiento);
        }
    }

    private void aplicarRevertirEstadisticasEquipos(Enfrentamiento enfrentamiento) {
        estadisticasEquipoUseCase.revertirEstadisticasEquipo(
                enfrentamiento.getGolesLocal(),
                enfrentamiento.getGolesVisitante(),
                enfrentamiento.getEquipoLocalId(),
                enfrentamiento.getEquipoVisitanteId()
        );
    }

    private void aplicarActualizacionEstadisticas(Enfrentamiento enfrentamiento) {
        estadisticasEquipoUseCase.actualizarEstadisticas(
                enfrentamiento.getEquipoLocalId(),
                enfrentamiento.getEquipoVisitanteId(),
                enfrentamiento.getGolesLocal(),
                enfrentamiento.getGolesVisitante());
    }

    private void aplicarActualizacionEnfrentamiento(ActualizarEnfrentamientoApplicationDTO actualizarEnfrentamientoApplicationDTO, Enfrentamiento enfrentamiento) {
        enfrentamiento.actualizar(
                actualizarEnfrentamientoApplicationDTO.fechaHora(),
                actualizarEnfrentamientoApplicationDTO.cancha(),
                actualizarEnfrentamientoApplicationDTO.estadoEnfrentamiento(),
                actualizarEnfrentamientoApplicationDTO.golesLocal(),
                actualizarEnfrentamientoApplicationDTO.golesVisitante()
        );
    }

    private void procesarGolesJugadoresEnfrentamiento(Enfrentamiento enfrentamiento,
                                                      List<GolesJugadorApplicationDTO> golesJugadoresLocal,
                                                      List<GolesJugadorApplicationDTO> golesJugadoresVisitante,
                                                      List<GolesJugador> golesExistentes) {
        golesJugadorUseCase.procesarGolesJugadores(
                enfrentamiento.getId(),
                enfrentamiento.getEquipoLocalId(),
                enfrentamiento.getEquipoVisitanteId(),
                golesJugadoresLocal,
                golesJugadoresVisitante,
                golesExistentes
        );
    }

    private List<GolesJugador> obtenerGolesExistentes(Long idEnfrentamiento) {
        return golesJugadorUseCase.obtenerGolesJugadoresPorEnfrentamiento(idEnfrentamiento);
    }

    private Enfrentamiento buscarEnfrentamiento(Long enfrentamientoId) {
        return enfrentamientoRepositoryPort.findById(enfrentamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Enfrentamiento", enfrentamientoId));
    }

}
