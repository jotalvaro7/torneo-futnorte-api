package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.application.dto.ActualizarEnfrentamientoApplicationDTO;
import com.futnorte.torneo.application.dto.EnfrentamientoResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EnfrentamientoUseCase {

    EnfrentamientoResponseDTO crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                     LocalDateTime fechaHora, String cancha);

    EnfrentamientoResponseDTO actualizarEnfrentamiento(ActualizarEnfrentamientoApplicationDTO actualizarEnfrentamientoApplicationDTO);

    Optional<EnfrentamientoResponseDTO> obtenerEnfrentamientoPorId(Long enfrentamientoId);

    List<EnfrentamientoResponseDTO> obtenerEnfrentamientosPorTorneo(Long torneoId);

    List<EnfrentamientoResponseDTO> obtenerEnfrentamientosPorEquipo(Long equipoId);

    List<EnfrentamientoResponseDTO> obtenerEnfrentamientosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    void eliminarEnfrentamiento(Long enfrentamientoId);

}