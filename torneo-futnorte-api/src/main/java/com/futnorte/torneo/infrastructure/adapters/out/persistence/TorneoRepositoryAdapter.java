package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.EstadoTorneo;
import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.domain.ports.out.TorneoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TorneoRepositoryAdapter implements TorneoRepositoryPort {
    
    private final TorneoJpaRepository jpaRepository;
    private final TorneoMapper mapper;
    
    @Override
    public Torneo save(Torneo torneo) {
        TorneoEntity entity = mapper.toEntity(torneo);
        TorneoEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Torneo> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Torneo> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
    
    @Override
    public Optional<Torneo> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Torneo> findByEstado(String estado) {
        EstadoTorneo estadoEnum = EstadoTorneo.valueOf(estado);
        return jpaRepository.findByEstado(estadoEnum).stream()
                .map(mapper::toDomain)
                .toList();
    }
}