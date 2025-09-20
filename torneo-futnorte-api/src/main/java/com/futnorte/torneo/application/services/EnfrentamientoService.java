package com.futnorte.torneo.application.services;

import com.futnorte.torneo.application.commands.ActualizacionEnfrentamientoCommand;
import com.futnorte.torneo.application.handlers.ActualizarEnfrentamientoHandler;
import com.futnorte.torneo.application.validators.EnfrentamientoValidator;
import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.domain.ports.out.EnfrentamientoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class EnfrentamientoService implements EnfrentamientoUseCase {

    private final EnfrentamientoRepositoryPort enfrentamientoRepository;
    private final EnfrentamientoValidator enfrentamientoValidator;
    private final ActualizarEnfrentamientoHandler actualizarEnfrentamientoHandler;

    @Override
    public Enfrentamiento crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                              LocalDateTime fechaHora, String cancha) {

        enfrentamientoValidator.validarCreacionEnfrentamiento(torneoId, equipoLocalId, equipoVisitanteId);
        Enfrentamiento enfrentamiento = new Enfrentamiento(torneoId, equipoLocalId, equipoVisitanteId, fechaHora, cancha);
        enfrentamiento.validarEnfrentamiento();

        log.info("Creando enfrentamiento torneoId={}, local={}, visitante={}", torneoId, equipoLocalId, equipoVisitanteId);

        return enfrentamientoRepository.save(enfrentamiento);

    }

    @Override
    public Enfrentamiento actualizarEnfrentamiento(ActualizacionEnfrentamientoCommand command) {
        return actualizarEnfrentamientoHandler.handle(command);
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
        log.debug("Enfrentamiento con id {} eliminado.", enfrentamientoId);
    }


    private Enfrentamiento buscarEnfrentamiento(Long enfrentamientoId) {
        return enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Enfrentamiento", enfrentamientoId));
    }

}
