package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorJpaRepository extends JpaRepository<JugadorEntity, Long> {
    
    List<JugadorEntity> findByEquipoId(Long equipoId);
    
    Optional<JugadorEntity> findByIdentificacion(String identificacion);
    
    boolean existsByIdentificacion(String identificacion);
    
    void deleteByEquipoId(Long equipoId);
    
    @Query("SELECT j FROM JugadorEntity j WHERE j.equipoId IN " +
           "(SELECT e.id FROM EquipoEntity e WHERE e.torneoId = :torneoId) " +
           "AND j.numeroGoles > 0 ORDER BY j.numeroGoles DESC")
    List<JugadorEntity> findGoleadoresByTorneoId(@Param("torneoId") Long torneoId);
}