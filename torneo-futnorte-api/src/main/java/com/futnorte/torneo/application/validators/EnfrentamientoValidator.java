package com.futnorte.torneo.application.validators;

import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.entities.EstadoTorneo;
import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.exceptions.ValidationException;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import com.futnorte.torneo.domain.ports.out.TorneoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator que orquesta validaciones de casos de uso relacionados con enfrentamientos.
 * Pertenece a la capa de Application ya que coordina validaciones que requieren
 * acceso a múltiples repositorios y reglas de negocio complejas.
 */
@RequiredArgsConstructor
@Component
public class EnfrentamientoValidator {

    private final TorneoRepositoryPort torneoRepository;
    private final EquipoRepositoryPort equipoRepository;

    /**
     * Valida que se pueda crear un enfrentamiento con los datos proporcionados.
     * Incluye validaciones que requieren acceso a datos persistidos.
     */
    public void validarCreacionEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId) {

        // Obtener entidades (con validación de existencia)
        Torneo torneo = obtenerTorneoExistente(torneoId);
        Equipo equipoLocal = obtenerEquipoExistente(equipoLocalId);
        Equipo equipoVisitante = obtenerEquipoExistente(equipoVisitanteId);

        // Validaciones de reglas de negocio
        validarEquiposPertenecenAlTorneo(torneo, equipoLocal, equipoVisitante);
        validarTorneoActivo(torneo);
    }

    /**
     * Valida que se pueda actualizar un enfrentamiento.
     */
    public void validarActualizacionEnfrentamiento(Long enfrentamientoId, Long torneoId,
                                                   Long equipoLocalId, Long equipoVisitanteId) {

        if (enfrentamientoId == null) {
            throw new ValidationException("enfrentamientoId", "no puede ser nulo");
        }

        // Validaciones adicionales específicas para actualización
        Torneo torneo = obtenerTorneoExistente(torneoId);
    }

    // Métodos privados de validación


    private Torneo obtenerTorneoExistente(Long torneoId) {
        return torneoRepository.findById(torneoId)
                .orElseThrow(() -> new EntityNotFoundException("Torneo", torneoId));
    }

    private Equipo obtenerEquipoExistente(Long equipoId) {
        return equipoRepository.buscarPorId(equipoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipo", equipoId));
    }

    private void validarEquiposPertenecenAlTorneo(Torneo torneo, Equipo equipoLocal, Equipo equipoVisitante) {
        if (!equipoLocal.getTorneoId().equals(torneo.getId())) {
            throw new ValidationException("equipoLocal", "debe pertenecer al torneo especificado");
        }

        if (!equipoVisitante.getTorneoId().equals(torneo.getId())) {
            throw new ValidationException("equipoVisitante", "debe pertenecer al torneo especificado");
        }
    }

    private void validarTorneoActivo(Torneo torneo) {
        if (!torneo.getEstado().equals(EstadoTorneo.EN_CURSO)) {
            throw new ValidationException("torneo", "debe estar activo para crear enfrentamientos");
        }
    }

}

