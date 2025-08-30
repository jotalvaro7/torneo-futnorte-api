package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.Enfrentamiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EnfrentamientoUseCase {
    
    Enfrentamiento crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId, 
                                     LocalDateTime fechaHora, String cancha);
    
    Enfrentamiento actualizarEnfrentamiento(Long enfrentamientoId, LocalDateTime fechaHora, String cancha);
    
    Enfrentamiento registrarResultado(Long enfrentamientoId, int golesLocal, int golesVisitante);
    
    Enfrentamiento cancelarEnfrentamiento(Long enfrentamientoId);
    
    Optional<Enfrentamiento> obtenerEnfrentamientoPorId(Long enfrentamientoId);
    
    List<Enfrentamiento> obtenerEnfrentamientosPorTorneo(Long torneoId);
    
    List<Enfrentamiento> obtenerEnfrentamientosPorEquipo(Long equipoId);
    
    List<Enfrentamiento> obtenerEnfrentamientosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    void eliminarEnfrentamiento(Long enfrentamientoId);
}