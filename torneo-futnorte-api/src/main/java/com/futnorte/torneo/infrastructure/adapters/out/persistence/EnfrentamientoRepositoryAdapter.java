package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.ports.out.EnfrentamientoRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EnfrentamientoRepositoryAdapter implements EnfrentamientoRepositoryPort {
    
    private final EnfrentamientoJpaRepository enfrentamientoJpaRepository;
    private final EnfrentamientoMapper enfrentamientoMapper;
    
    public EnfrentamientoRepositoryAdapter(EnfrentamientoJpaRepository enfrentamientoJpaRepository,
                                         EnfrentamientoMapper enfrentamientoMapper) {
        this.enfrentamientoJpaRepository = enfrentamientoJpaRepository;
        this.enfrentamientoMapper = enfrentamientoMapper;
    }
    
    @Override
    public Enfrentamiento save(Enfrentamiento enfrentamiento) {
        EnfrentamientoEntity entity = enfrentamientoMapper.toEntity(enfrentamiento);
        EnfrentamientoEntity savedEntity = enfrentamientoJpaRepository.save(entity);
        return enfrentamientoMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Enfrentamiento> findById(Long id) {
        return enfrentamientoJpaRepository.findById(id)
                .map(enfrentamientoMapper::toDomain);
    }
    
    @Override
    public List<Enfrentamiento> findAll() {
        return enfrentamientoJpaRepository.findAll()
                .stream()
                .map(enfrentamientoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Enfrentamiento> findByTorneoId(Long torneoId) {
        return enfrentamientoJpaRepository.findByTorneoId(torneoId)
                .stream()
                .map(enfrentamientoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Enfrentamiento> findByEquipoLocalIdOrEquipoVisitanteId(Long equipoId, Long equipoId2) {
        return enfrentamientoJpaRepository.findByEquipoLocalIdOrEquipoVisitanteId(equipoId, equipoId2)
                .stream()
                .map(enfrentamientoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Enfrentamiento> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return enfrentamientoJpaRepository.findByFechaHoraBetween(fechaInicio, fechaFin)
                .stream()
                .map(enfrentamientoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        enfrentamientoJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return enfrentamientoJpaRepository.existsById(id);
    }
}