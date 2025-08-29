package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoJpaRepository extends JpaRepository<EquipoEntity, Long> {

    boolean existsByNombreAndTorneoId(String nombre, Long torneoId);

    @Query("SELECT e FROM EquipoEntity e WHERE e.torneoId = :torneoId ORDER BY e.puntos DESC, (e.golesAFavor - e.golesEnContra) DESC, e.golesAFavor DESC")
    List<EquipoEntity> findByTorneoIdOrderByPuntosDesc(@Param("torneoId") Long torneoId);
}