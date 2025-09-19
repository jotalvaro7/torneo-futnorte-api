package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorDto;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.exceptions.ValidationException;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.domain.ports.out.EnfrentamientoRepositoryPort;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import com.futnorte.torneo.domain.ports.out.GolesJugadorRepositoryPort;
import com.futnorte.torneo.domain.ports.out.JugadorRepositoryPort;
import com.futnorte.torneo.domain.ports.out.TorneoRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnfrentamientoService implements EnfrentamientoUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EnfrentamientoService.class);

    private final EnfrentamientoRepositoryPort enfrentamientoRepository;
    private final EquipoRepositoryPort equipoRepository;
    private final TorneoRepositoryPort torneoRepository;
    private final JugadorRepositoryPort jugadorRepository;
    private final GolesJugadorRepositoryPort golesJugadorRepository;

    public EnfrentamientoService(EnfrentamientoRepositoryPort enfrentamientoRepository,
                                 EquipoRepositoryPort equipoRepository,
                                 TorneoRepositoryPort torneoRepository,
                                 JugadorRepositoryPort jugadorRepository,
                                 GolesJugadorRepositoryPort golesJugadorRepository) {
        this.enfrentamientoRepository = enfrentamientoRepository;
        this.equipoRepository = equipoRepository;
        this.torneoRepository = torneoRepository;
        this.jugadorRepository = jugadorRepository;
        this.golesJugadorRepository = golesJugadorRepository;
    }

    /* ---------------------- Métodos del caso de uso (@Override) ---------------------- */

    @Override
    public Enfrentamiento crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                              LocalDateTime fechaHora, String cancha) {

        Torneo torneo = obtenerTorneo(torneoId);
        Equipo equipoLocal = obtenerEquipo(equipoLocalId);
        Equipo equipoVisitante = obtenerEquipo(equipoVisitanteId);

        validarEquiposPertenecenAlTorneo(torneoId, equipoLocal, equipoVisitante);

        Enfrentamiento enfrentamiento = new Enfrentamiento(
                torneoId, equipoLocalId, equipoVisitanteId, fechaHora, cancha
        );
        enfrentamiento.validarEnfrentamiento();

        return enfrentamientoRepository.save(enfrentamiento);
    }

    @Override
    public Enfrentamiento actualizarEnfrentamiento(Long enfrentamientoId, LocalDateTime fechaHora, String cancha,
                                                   EstadoEnfrentamiento estado, Integer golesLocal, Integer golesVisitante,
                                                   List<GolesJugadorDto> golesJugadoresLocal, List<GolesJugadorDto> golesJugadoresVisitante) {

        Enfrentamiento enfrentamiento = buscarEnfrentamiento(enfrentamientoId);

        enfrentamiento.actualizar(fechaHora, cancha, estado, golesLocal, golesVisitante);

        actualizarEstadisticasEquiposSiEsNecesario(enfrentamiento, estado, golesLocal, golesVisitante);

        List<GolesJugador> golesExistentesAntes = obtenerGolesExistentesAntes(enfrentamiento);

        Enfrentamiento enfrentamientoGuardado = persistirEnfrentamiento(enfrentamiento);

        procesarGolesJugadoresSiSeProveen(enfrentamientoGuardado, golesJugadoresLocal, golesJugadoresVisitante, golesExistentesAntes);

        return enfrentamientoGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enfrentamiento> obtenerEnfrentamientoPorId(Long enfrentamientoId) {
        return enfrentamientoRepository.findById(enfrentamientoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enfrentamiento> obtenerEnfrentamientosPorTorneo(Long torneoId) {
        return enfrentamientoRepository.findByTorneoId(torneoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enfrentamiento> obtenerEnfrentamientosPorEquipo(Long equipoId) {
        return enfrentamientoRepository.findByEquipoLocalIdOrEquipoVisitanteId(equipoId, equipoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enfrentamiento> obtenerEnfrentamientosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return enfrentamientoRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
    }

    @Override
    public void eliminarEnfrentamiento(Long enfrentamientoId) {
        Enfrentamiento enfrentamiento = buscarEnfrentamiento(enfrentamientoId);
        enfrentamientoRepository.deleteById(enfrentamientoId);
        logger.debug("Enfrentamiento con id {} eliminado.", enfrentamientoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId) {
        return golesJugadorRepository.findByEnfrentamientoId(enfrentamientoId);
    }

    /* ---------------------- Métodos privados extraídos y refactorizados ---------------------- */

    private void actualizarEstadisticasEquiposSiEsNecesario(Enfrentamiento enfrentamiento, EstadoEnfrentamiento estado,
                                                            Integer golesLocal, Integer golesVisitante) {
        if (estado == EstadoEnfrentamiento.FINALIZADO && golesLocal != null && golesVisitante != null) {
            Equipo equipoLocal = obtenerEquipo(enfrentamiento.getEquipoLocalId());
            Equipo equipoVisitante = obtenerEquipo(enfrentamiento.getEquipoVisitanteId());

            actualizarEstadisticasEquipos(golesLocal, golesVisitante, equipoLocal, equipoVisitante);

            equipoRepository.guardar(equipoLocal);
            equipoRepository.guardar(equipoVisitante);
        }
    }

    /**
     * Actualiza las estadísticas de los equipos (victorias, derrotas, empates, goles)
     * según el resultado del enfrentamiento.
     */
    private void actualizarEstadisticasEquipos(int golesLocal, int golesVisitante,
                                               Equipo equipoLocal, Equipo equipoVisitante) {
        if (golesLocal == golesVisitante) {
            // Empate
            equipoLocal.registrarEmpate(golesLocal, golesVisitante);
            equipoVisitante.registrarEmpate(golesVisitante, golesLocal);
            logger.debug("Empate registrado: {}-{}", golesLocal, golesVisitante);
        } else if (golesLocal > golesVisitante) {
            // Victoria local
            equipoLocal.registrarVictoria(golesLocal, golesVisitante);
            equipoVisitante.registrarDerrota(golesVisitante, golesLocal);
            logger.debug("Victoria local {}-{}", golesLocal, golesVisitante);
        } else {
            // Victoria visitante
            equipoVisitante.registrarVictoria(golesVisitante, golesLocal);
            equipoLocal.registrarDerrota(golesLocal, golesVisitante);
            logger.debug("Victoria visitante {}-{}", golesVisitante, golesLocal);
        }
    }


    private List<GolesJugador> obtenerGolesExistentesAntes(Enfrentamiento enfrentamiento) {
        try {
            logger.debug("Obteniendo goles existentes ANTES de modificar nada...");
            return golesJugadorRepository.findByEnfrentamientoId(enfrentamiento.getId());
        } catch (Exception e) {
            logger.warn("Error al obtener goles existentes: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private Enfrentamiento persistirEnfrentamiento(Enfrentamiento enfrentamiento) {
        Enfrentamiento guardado = enfrentamientoRepository.save(enfrentamiento);
        logger.debug("Enfrentamiento guardado con ID: {}", guardado.getId());
        return guardado;
    }

    private void procesarGolesJugadoresSiSeProveen(Enfrentamiento enfrentamiento,
                                                   List<GolesJugadorDto> golesJugadoresLocal,
                                                   List<GolesJugadorDto> golesJugadoresVisitante,
                                                   List<GolesJugador> golesExistentesAntes) {
        if (!hayGolesJugadoresParaProcesar(golesJugadoresLocal, golesJugadoresVisitante)) {
            logger.debug("No se detectaron goles de jugadores para procesar");
            return;
        }

        logger.debug("Procesando goles de jugadores para el enfrentamiento ID: {}", enfrentamiento.getId());
        try {
            procesarGolesJugadoresConHistorial(enfrentamiento, golesJugadoresLocal, golesJugadoresVisitante, golesExistentesAntes);
        } catch (Exception e) {
            logger.error("Error al procesar goles de jugadores: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar goles de jugadores: " + e.getMessage(), e);
        }
    }

    private boolean hayGolesJugadoresParaProcesar(List<GolesJugadorDto> golesJugadoresLocal,
                                                  List<GolesJugadorDto> golesJugadoresVisitante) {
        return (golesJugadoresLocal != null && !golesJugadoresLocal.isEmpty()) ||
                (golesJugadoresVisitante != null && !golesJugadoresVisitante.isEmpty());
    }

    private void procesarGolesJugadoresConHistorial(Enfrentamiento enfrentamiento,
                                                    List<GolesJugadorDto> golesJugadoresLocal,
                                                    List<GolesJugadorDto> golesJugadoresVisitante,
                                                    List<GolesJugador> golesExistentesAntes) {

        logger.debug("Procesando goles de jugadores para enfrentamiento ID: {} con historial previo", enfrentamiento.getId());

        // 1) Revertir goles anteriores (si los hay)
        revertirGolesAnteriores(enfrentamiento, golesExistentesAntes);

        // 2) Eliminar registros anteriores
        eliminarGolesAnteriores(enfrentamiento);

        // 3) Registrar nuevos goles (locales y visitantes)
        List<GolesJugador> nuevosGoles = new ArrayList<>();
        nuevosGoles.addAll(registrarNuevosGoles(enfrentamiento, golesJugadoresLocal, enfrentamiento.getEquipoLocalId()));
        nuevosGoles.addAll(registrarNuevosGoles(enfrentamiento, golesJugadoresVisitante, enfrentamiento.getEquipoVisitanteId()));

        // 4) Persistir nuevos goles
        persistirNuevosGoles(enfrentamiento, nuevosGoles);
    }

    private void revertirGolesAnteriores(Enfrentamiento enfrentamiento, List<GolesJugador> golesExistentesAntes) {
        if (golesExistentesAntes == null || golesExistentesAntes.isEmpty()) {
            logger.debug("No hay goles existentes para revertir en enfrentamiento ID: {}", enfrentamiento.getId());
            return;
        }

        logger.debug("Revirtiendo {} goles existentes del historial", golesExistentesAntes.size());
        for (GolesJugador golesExistente : golesExistentesAntes) {
            try {
                Jugador jugador = jugadorRepository.buscarPorId(golesExistente.getJugadorId())
                        .orElseThrow(() -> new EntityNotFoundException("Jugador", golesExistente.getJugadorId()));

                jugador.revertirGoles(golesExistente.getCantidadGoles());
                jugadorRepository.guardar(jugador);

                logger.debug("Revertidos {} goles del jugador {}, ahora tiene {} goles",
                        golesExistente.getCantidadGoles(), jugador.getNombre(), jugador.getNumeroGoles());
            } catch (Exception e) {
                logger.warn("Error al revertir goles para jugador ID {} en enfrentamiento {}: {}",
                        golesExistente.getJugadorId(), enfrentamiento.getId(), e.getMessage());
            }
        }
    }

    private void eliminarGolesAnteriores(Enfrentamiento enfrentamiento) {
        try {
            golesJugadorRepository.deleteByEnfrentamientoId(enfrentamiento.getId());
            logger.debug("Eliminados goles existentes para enfrentamiento ID: {}", enfrentamiento.getId());
        } catch (Exception e) {
            logger.warn("Error al eliminar goles anteriores para enfrentamiento ID {}: {}", enfrentamiento.getId(), e.getMessage());
        }
    }

    private List<GolesJugador> registrarNuevosGoles(Enfrentamiento enfrentamiento,
                                                    List<GolesJugadorDto> golesJugadores,
                                                    Long equipoEsperado) {
        List<GolesJugador> nuevosGoles = new ArrayList<>();
        if (golesJugadores == null || golesJugadores.isEmpty()) {
            return nuevosGoles;
        }

        logger.debug("Procesando {} goles para equipo {}", golesJugadores.size(), equipoEsperado);

        for (GolesJugadorDto golesDto : golesJugadores) {
            try {
                registrarGolesJugadorInterno(enfrentamiento, golesDto.getJugadorId(),
                        golesDto.getCantidadGoles(), equipoEsperado);

                nuevosGoles.add(new GolesJugador(enfrentamiento.getId(),
                        golesDto.getJugadorId(), golesDto.getCantidadGoles()));
            } catch (Exception e) {
                logger.warn("Error al procesar gol para jugador ID {}: {}. Continuando con el siguiente.",
                        golesDto.getJugadorId(), e.getMessage());
            }
        }
        return nuevosGoles;
    }

    private void persistirNuevosGoles(Enfrentamiento enfrentamiento, List<GolesJugador> nuevosGoles) {
        if (nuevosGoles == null || nuevosGoles.isEmpty()) {
            logger.debug("No hay goles nuevos para persistir en enfrentamiento ID {}", enfrentamiento.getId());
            return;
        }

        logger.debug("Guardando {} registros de goles en la base de datos", nuevosGoles.size());
        List<GolesJugador> golesPersistidos = golesJugadorRepository.saveAll(nuevosGoles);
        logger.debug("Persistidos exitosamente {} goles", golesPersistidos.size());

        // Verificación inmediata (opcional, dejar si ayuda en debugging)
        List<GolesJugador> verificacion = golesJugadorRepository.findByEnfrentamientoId(enfrentamiento.getId());
        if (verificacion.isEmpty()) {
            logger.error("ERROR CRÍTICO: No se pueden leer los goles inmediatamente después de guardarlos!");
        } else {
            logger.debug("ÉXITO: Los goles son legibles inmediatamente después del guardado");
        }
    }

    private void registrarGolesJugadorInterno(Enfrentamiento enfrentamiento, Long jugadorId,
                                              int cantidadGoles, Long equipoEsperado) {
        Jugador jugador = jugadorRepository.buscarPorId(jugadorId)
                .orElseThrow(() -> new EntityNotFoundException("Jugador", jugadorId));

        if (!jugador.getEquipoId().equals(equipoEsperado)) {
            throw new ValidationException("jugador", "debe pertenecer al equipo correspondiente en el enfrentamiento");
        }

        jugador.anotarGoles(cantidadGoles);
        jugadorRepository.guardar(jugador);
    }

    private Torneo obtenerTorneo(Long torneoId) {
        return torneoRepository.findById(torneoId)
                .orElseThrow(() -> new EntityNotFoundException("Torneo", torneoId));
    }

    private Equipo obtenerEquipo(Long equipoId) {
        return equipoRepository.buscarPorId(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo", equipoId));
    }

    private void validarEquiposPertenecenAlTorneo(Long torneoId, Equipo local, Equipo visitante) {
        if (!local.getTorneoId().equals(torneoId) || !visitante.getTorneoId().equals(torneoId)) {
            throw new ValidationException("Ambos equipos deben pertenecer al torneo especificado");
        }
    }

    private Enfrentamiento buscarEnfrentamiento(Long enfrentamientoId) {
        return enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Enfrentamiento", enfrentamientoId));
    }

}
