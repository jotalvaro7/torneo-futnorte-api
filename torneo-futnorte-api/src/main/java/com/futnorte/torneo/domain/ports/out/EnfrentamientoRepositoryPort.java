package com.futnorte.torneo.domain.ports.out;

import com.futnorte.torneo.domain.entities.Enfrentamiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EnfrentamientoRepositoryPort {
    
    Enfrentamiento save(Enfrentamiento enfrentamiento);
    
    Optional<Enfrentamiento> findById(Long id);
    
    List<Enfrentamiento> findAll();
    
    List<Enfrentamiento> findByTorneoId(Long torneoId);
    
    List<Enfrentamiento> findByEquipoLocalIdOrEquipoVisitanteId(Long equipoId, Long equipoId2);
    
    List<Enfrentamiento> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}