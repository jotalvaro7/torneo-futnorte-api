package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.EstadoTorneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TorneoJpaRepository extends JpaRepository<TorneoEntity, Long> {
    
    Optional<TorneoEntity> findByNombre(String nombre);
    
    List<TorneoEntity> findByEstado(EstadoTorneo estado);
}