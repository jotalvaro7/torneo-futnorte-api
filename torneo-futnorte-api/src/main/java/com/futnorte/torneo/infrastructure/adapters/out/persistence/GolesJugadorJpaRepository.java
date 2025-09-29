package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GolesJugadorJpaRepository extends JpaRepository<GolesJugadorEntity, Long> {

    List<GolesJugadorEntity> findByEnfrentamientoId(Long enfrentamientoId);

    @Modifying
    @Query("DELETE FROM GolesJugadorEntity g WHERE g.enfrentamientoId = :enfrentamientoId")
    void deleteByEnfrentamientoId(@Param("enfrentamientoId") Long enfrentamientoId);
}