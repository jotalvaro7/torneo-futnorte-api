package com.futnorte.torneo.domain.ports.out;

import com.futnorte.torneo.domain.entities.Torneo;

import java.util.List;
import java.util.Optional;

public interface TorneoRepositoryPort {
    
    Torneo save(Torneo torneo);
    
    Optional<Torneo> findById(Long id);
    
    List<Torneo> findAll();
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    Optional<Torneo> findByNombre(String nombre);
    
    List<Torneo> findByEstado(String estado);
}