package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorJpaRepository extends JpaRepository<JugadorEntity, Long> {
    
    List<JugadorEntity> findByEquipoId(Long equipoId);
    
    Optional<JugadorEntity> findByIdentificacion(String identificacion);
    
    boolean existsByIdentificacion(String identificacion);
    
    void deleteByEquipoId(Long equipoId);
}