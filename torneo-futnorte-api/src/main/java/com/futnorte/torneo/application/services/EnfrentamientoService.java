package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorDto;
import com.futnorte.torneo.domain.exceptions.BusinessRuleException;
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
    
    @Override
    public Enfrentamiento crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                            LocalDateTime fechaHora, String cancha) {
        
        Torneo torneo = torneoRepository.findById(torneoId)
                .orElseThrow(() -> new EntityNotFoundException("Torneo", torneoId));
        
        Equipo equipoLocal = equipoRepository.buscarPorId(equipoLocalId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo", equipoLocalId));
        
        Equipo equipoVisitante = equipoRepository.buscarPorId(equipoVisitanteId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo", equipoVisitanteId));
        
        if (!equipoLocal.getTorneoId().equals(torneoId) || 
            !equipoVisitante.getTorneoId().equals(torneoId)) {
            throw new ValidationException("Ambos equipos deben pertenecer al torneo especificado");
        }
        
        Enfrentamiento enfrentamiento = new Enfrentamiento(torneoId, equipoLocalId, equipoVisitanteId, 
                                                          fechaHora, cancha);
        enfrentamiento.validarEnfrentamiento();
        
        return enfrentamientoRepository.save(enfrentamiento);
    }
    
    @Override
    public Enfrentamiento actualizarEnfrentamiento(Long enfrentamientoId, LocalDateTime fechaHora, String cancha,
                                                 EstadoEnfrentamiento estado, Integer golesLocal, Integer golesVisitante,
                                                 List<GolesJugadorDto> golesJugadoresLocal, List<GolesJugadorDto> golesJugadoresVisitante) {
        Enfrentamiento enfrentamiento = enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Enfrentamiento", enfrentamientoId));

        enfrentamiento.actualizar(fechaHora, cancha, estado, golesLocal, golesVisitante);

        // Actualizar estadísticas de equipos si el enfrentamiento se finaliza
        if (estado != null && estado == EstadoEnfrentamiento.FINALIZADO && golesLocal != null && golesVisitante != null) {
            Equipo equipoLocal = equipoRepository.buscarPorId(enfrentamiento.getEquipoLocalId())
                    .orElseThrow(() -> new EntityNotFoundException("Equipo", enfrentamiento.getEquipoLocalId()));

            Equipo equipoVisitante = equipoRepository.buscarPorId(enfrentamiento.getEquipoVisitanteId())
                    .orElseThrow(() -> new EntityNotFoundException("Equipo", enfrentamiento.getEquipoVisitanteId()));

            actualizarEstadisticasEquipos(golesLocal, golesVisitante, equipoLocal, equipoVisitante);

            equipoRepository.guardar(equipoLocal);
            equipoRepository.guardar(equipoVisitante);
        }

        // PRIMERO: Obtener goles existentes ANTES de cualquier otra operación
        List<GolesJugador> golesExistentesAntes = new ArrayList<>();
        try {
            logger.debug("Obteniendo goles existentes ANTES de modificar nada...");
            golesExistentesAntes = golesJugadorRepository.findByEnfrentamientoId(enfrentamiento.getId());
            logger.debug("Encontrados {} goles existentes antes de la actualización", golesExistentesAntes.size());
        } catch (Exception e) {
            logger.warn("Error al obtener goles existentes: {}", e.getMessage());
        }

        // SEGUNDO: Guardar el enfrentamiento
        Enfrentamiento enfrentamientoGuardado = enfrentamientoRepository.save(enfrentamiento);
        logger.debug("Enfrentamiento guardado con ID: {}", enfrentamientoGuardado.getId());

        // TERCERO: Procesar goles de jugadores siempre que se envíen (independiente del estado)
        if ((golesJugadoresLocal != null && !golesJugadoresLocal.isEmpty()) ||
            (golesJugadoresVisitante != null && !golesJugadoresVisitante.isEmpty())) {
            logger.debug("Procesando goles de jugadores para el enfrentamiento");
            try {
                procesarGolesJugadoresConHistorial(enfrentamientoGuardado, golesJugadoresLocal, golesJugadoresVisitante, golesExistentesAntes);
            } catch (Exception e) {
                logger.error("Error al procesar goles de jugadores: {}", e.getMessage(), e);
                throw new RuntimeException("Error al procesar goles de jugadores: " + e.getMessage(), e);
            }
        } else {
            logger.debug("No se detectaron goles de jugadores para procesar");
        }

        return enfrentamientoGuardado;
    }

    private void procesarGolesJugadoresConHistorial(Enfrentamiento enfrentamiento,
                                                  List<GolesJugadorDto> golesJugadoresLocal,
                                                  List<GolesJugadorDto> golesJugadoresVisitante,
                                                  List<GolesJugador> golesExistentesAntes) {

        logger.debug("Procesando goles de jugadores para enfrentamiento ID: {} con historial previo", enfrentamiento.getId());

        // Primero revertir goles existentes usando el historial pasado como parámetro
        try {
            if (golesExistentesAntes.isEmpty()) {
                logger.debug("No hay goles existentes para revertir en enfrentamiento ID: {}", enfrentamiento.getId());
            } else {
                logger.debug("Revirtiendo {} goles existentes del historial", golesExistentesAntes.size());
                for (GolesJugador golesExistente : golesExistentesAntes) {
                    logger.debug("Procesando goles existentes del historial: Jugador ID: {}, Cantidad: {}",
                               golesExistente.getJugadorId(), golesExistente.getCantidadGoles());

                    Jugador jugador = jugadorRepository.buscarPorId(golesExistente.getJugadorId())
                            .orElseThrow(() -> new EntityNotFoundException("Jugador", golesExistente.getJugadorId()));

                    logger.debug("Jugador {} tenía {} goles antes de revertir", jugador.getNombre(), jugador.getNumeroGoles());

                    // Revertir los goles que se habían sumado anteriormente
                    jugador.revertirGoles(golesExistente.getCantidadGoles());
                    jugadorRepository.guardar(jugador);

                    logger.debug("Revertidos {} goles del jugador {}, ahora tiene {} goles",
                               golesExistente.getCantidadGoles(), jugador.getNombre(), jugador.getNumeroGoles());
                }
            }

            // Ahora eliminar los registros de goles
            golesJugadorRepository.deleteByEnfrentamientoId(enfrentamiento.getId());
            logger.debug("Eliminados goles existentes para enfrentamiento ID: {}", enfrentamiento.getId());
        } catch (Exception e) {
            logger.warn("Error al procesar goles existentes para enfrentamiento ID {}: {}. Continuando...",
                       enfrentamiento.getId(), e.getMessage());
        }

        List<GolesJugador> nuevosGoles = new ArrayList<>();

        // Procesar goles de jugadores locales
        if (golesJugadoresLocal != null && !golesJugadoresLocal.isEmpty()) {
            logger.debug("Procesando {} goles de jugadores locales", golesJugadoresLocal.size());
            for (GolesJugadorDto golesDto : golesJugadoresLocal) {
                try {
                    registrarGolesJugadorInterno(enfrentamiento, golesDto.getJugadorId(),
                                               golesDto.getCantidadGoles(), enfrentamiento.getEquipoLocalId());

                    // Crear entidad de goles para persistir solo si la validación fue exitosa
                    GolesJugador golesJugador = new GolesJugador(enfrentamiento.getId(),
                            golesDto.getJugadorId(), golesDto.getCantidadGoles());
                    nuevosGoles.add(golesJugador);

                } catch (Exception e) {
                    logger.warn("Error al procesar gol para jugador ID {}: {}. Continuando con el siguiente.",
                               golesDto.getJugadorId(), e.getMessage());
                }
            }
        }

        // Procesar goles de jugadores visitantes
        if (golesJugadoresVisitante != null && !golesJugadoresVisitante.isEmpty()) {
            logger.debug("Procesando {} goles de jugadores visitantes", golesJugadoresVisitante.size());
            for (GolesJugadorDto golesDto : golesJugadoresVisitante) {
                try {
                    registrarGolesJugadorInterno(enfrentamiento, golesDto.getJugadorId(),
                                               golesDto.getCantidadGoles(), enfrentamiento.getEquipoVisitanteId());

                    // Crear entidad de goles para persistir solo si la validación fue exitosa
                    GolesJugador golesJugador = new GolesJugador(enfrentamiento.getId(),
                            golesDto.getJugadorId(), golesDto.getCantidadGoles());
                    nuevosGoles.add(golesJugador);

                } catch (Exception e) {
                    logger.warn("Error al procesar gol para jugador ID {}: {}. Continuando con el siguiente.",
                               golesDto.getJugadorId(), e.getMessage());
                }
            }
        }

        // Persistir todos los goles
        if (!nuevosGoles.isEmpty()) {
            logger.debug("Guardando {} registros de goles en la base de datos", nuevosGoles.size());
            List<GolesJugador> golesPersistidos = golesJugadorRepository.saveAll(nuevosGoles);
            logger.debug("Persistidos exitosamente {} goles", golesPersistidos.size());
        } else {
            logger.debug("No hay goles nuevos para persistir");
        }
    }

    private void actualizarEstadisticasEquipos(Integer golesLocal, Integer golesVisitante,
                                             Equipo equipoLocal, Equipo equipoVisitante) {
        if (golesLocal > golesVisitante) {
            equipoLocal.registrarVictoria(golesLocal, golesVisitante);
            equipoVisitante.registrarDerrota(golesVisitante, golesLocal);
        } else if (golesLocal < golesVisitante) {
            equipoLocal.registrarDerrota(golesLocal, golesVisitante);
            equipoVisitante.registrarVictoria(golesVisitante, golesLocal);
        } else {
            equipoLocal.registrarEmpate(golesLocal, golesVisitante);
            equipoVisitante.registrarEmpate(golesVisitante, golesLocal);
        }
    }

    private void procesarGolesJugadores(Enfrentamiento enfrentamiento,
                                      List<GolesJugadorDto> golesJugadoresLocal,
                                      List<GolesJugadorDto> golesJugadoresVisitante) {

        logger.debug("Procesando goles de jugadores para enfrentamiento ID: {}", enfrentamiento.getId());

        // Primero revertir goles existentes antes de eliminarlos
        try {
            logger.debug("Buscando goles existentes para enfrentamiento ID: {}", enfrentamiento.getId());
            List<GolesJugador> golesExistentes = obtenerGolesJugadoresPorEnfrentamiento(enfrentamiento.getId());
            logger.debug("Encontrados {} goles existentes para enfrentamiento ID: {}", golesExistentes.size(), enfrentamiento.getId());

            if (golesExistentes.isEmpty()) {
                logger.debug("No hay goles existentes para revertir en enfrentamiento ID: {}", enfrentamiento.getId());
            } else {
                for (GolesJugador golesExistente : golesExistentes) {
                    logger.debug("Procesando goles existentes: Jugador ID: {}, Cantidad: {}",
                               golesExistente.getJugadorId(), golesExistente.getCantidadGoles());

                    Jugador jugador = jugadorRepository.buscarPorId(golesExistente.getJugadorId())
                            .orElseThrow(() -> new EntityNotFoundException("Jugador", golesExistente.getJugadorId()));

                    logger.debug("Jugador {} tenía {} goles antes de revertir", jugador.getNombre(), jugador.getNumeroGoles());

                    // Revertir los goles que se habían sumado anteriormente
                    jugador.revertirGoles(golesExistente.getCantidadGoles());
                    jugadorRepository.guardar(jugador);

                    logger.debug("Revertidos {} goles del jugador {}, ahora tiene {} goles",
                               golesExistente.getCantidadGoles(), jugador.getNombre(), jugador.getNumeroGoles());
                }
            }

            // Ahora eliminar los registros de goles
            golesJugadorRepository.deleteByEnfrentamientoId(enfrentamiento.getId());
            logger.debug("Eliminados goles existentes para enfrentamiento ID: {}", enfrentamiento.getId());
        } catch (Exception e) {
            logger.warn("Error al procesar goles existentes para enfrentamiento ID {}: {}. Continuando...",
                       enfrentamiento.getId(), e.getMessage());
        }

        List<GolesJugador> nuevosGoles = new ArrayList<>();

        // Procesar goles de jugadores locales
        if (golesJugadoresLocal != null && !golesJugadoresLocal.isEmpty()) {
            logger.debug("Procesando {} goles de jugadores locales", golesJugadoresLocal.size());
            for (GolesJugadorDto golesDto : golesJugadoresLocal) {
                try {
                    registrarGolesJugadorInterno(enfrentamiento, golesDto.getJugadorId(),
                                               golesDto.getCantidadGoles(), enfrentamiento.getEquipoLocalId());

                    // Crear entidad de goles para persistir solo si la validación fue exitosa
                    GolesJugador golesJugador = new GolesJugador(enfrentamiento.getId(),
                            golesDto.getJugadorId(), golesDto.getCantidadGoles());
                    nuevosGoles.add(golesJugador);

                } catch (Exception e) {
                    logger.warn("Error al procesar gol para jugador ID {}: {}. Continuando con el siguiente.",
                               golesDto.getJugadorId(), e.getMessage());
                }
            }
        }

        // Procesar goles de jugadores visitantes
        if (golesJugadoresVisitante != null && !golesJugadoresVisitante.isEmpty()) {
            logger.debug("Procesando {} goles de jugadores visitantes", golesJugadoresVisitante.size());
            for (GolesJugadorDto golesDto : golesJugadoresVisitante) {
                try {
                    registrarGolesJugadorInterno(enfrentamiento, golesDto.getJugadorId(),
                                               golesDto.getCantidadGoles(), enfrentamiento.getEquipoVisitanteId());

                    // Crear entidad de goles para persistir solo si la validación fue exitosa
                    GolesJugador golesJugador = new GolesJugador(enfrentamiento.getId(),
                            golesDto.getJugadorId(), golesDto.getCantidadGoles());
                    nuevosGoles.add(golesJugador);

                } catch (Exception e) {
                    logger.warn("Error al procesar gol para jugador ID {}: {}. Continuando con el siguiente.",
                               golesDto.getJugadorId(), e.getMessage());
                }
            }
        }

        // Persistir todos los goles
        if (!nuevosGoles.isEmpty()) {
            logger.debug("Guardando {} registros de goles en la base de datos", nuevosGoles.size());
            List<GolesJugador> golesPersistidos = golesJugadorRepository.saveAll(nuevosGoles);
            logger.debug("Persistidos exitosamente {} goles", golesPersistidos.size());

            // VERIFICACIÓN INMEDIATA: Intentar leer los goles que acabamos de guardar
            logger.debug("VERIFICACIÓN INMEDIATA: Intentando leer goles recién guardados...");
            List<GolesJugador> verificacion = golesJugadorRepository.findByEnfrentamientoId(enfrentamiento.getId());
            logger.debug("VERIFICACIÓN: Encontrados {} goles después del guardado", verificacion.size());

            if (verificacion.isEmpty()) {
                logger.error("ERROR CRÍTICO: No se pueden leer los goles inmediatamente después de guardarlos!");
            } else {
                logger.debug("ÉXITO: Los goles son legibles inmediatamente después del guardado");
            }
        } else {
            logger.debug("No hay goles nuevos para persistir");
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
        Enfrentamiento enfrentamiento = enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Enfrentamiento", enfrentamientoId));

        enfrentamientoRepository.deleteById(enfrentamientoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GolesJugador> obtenerGolesJugadoresPorEnfrentamiento(Long enfrentamientoId) {
        return golesJugadorRepository.findByEnfrentamientoId(enfrentamientoId);
    }
}