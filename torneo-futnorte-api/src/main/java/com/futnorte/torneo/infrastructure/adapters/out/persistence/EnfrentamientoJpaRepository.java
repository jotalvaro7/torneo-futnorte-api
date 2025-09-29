package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnfrentamientoJpaRepository extends JpaRepository<EnfrentamientoEntity, Long> {
    
    List<EnfrentamientoEntity> findByTorneoId(Long torneoId);
    
    List<EnfrentamientoEntity> findByEquipoLocalIdOrEquipoVisitanteId(Long equipoLocalId, Long equipoVisitanteId);
    
    List<EnfrentamientoEntity> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT e FROM EnfrentamientoEntity e WHERE e.torneoId = :torneoId AND e.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    List<EnfrentamientoEntity> findByTorneoIdAndFechaHoraBetween(@Param("torneoId") Long torneoId,
                                                                @Param("fechaInicio") LocalDateTime fechaInicio,
                                                                @Param("fechaFin") LocalDateTime fechaFin);

    @Modifying
    @Query(value = "DELETE FROM enfrentamientos WHERE id = :id", nativeQuery = true)
    void deleteEnfrentamientoById(@Param("id") Long id);
}

