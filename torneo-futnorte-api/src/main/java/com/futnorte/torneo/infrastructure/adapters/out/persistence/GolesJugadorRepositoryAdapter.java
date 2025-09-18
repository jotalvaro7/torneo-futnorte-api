package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.ports.out.GolesJugadorRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GolesJugadorRepositoryAdapter implements GolesJugadorRepositoryPort {

    private final GolesJugadorJpaRepository jpaRepository;
    private final GolesJugadorMapper mapper;

    public GolesJugadorRepositoryAdapter(GolesJugadorJpaRepository jpaRepository, GolesJugadorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public GolesJugador save(GolesJugador golesJugador) {
        GolesJugadorEntity entity = mapper.toEntity(golesJugador);
        GolesJugadorEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<GolesJugador> findByEnfrentamientoId(Long enfrentamientoId) {
        List<GolesJugadorEntity> entities = jpaRepository.findByEnfrentamientoId(enfrentamientoId);
        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByEnfrentamientoId(Long enfrentamientoId) {
        jpaRepository.deleteByEnfrentamientoId(enfrentamientoId);
    }

    @Override
    public List<GolesJugador> saveAll(List<GolesJugador> golesJugadores) {
        List<GolesJugadorEntity> entities = golesJugadores.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        List<GolesJugadorEntity> savedEntities = jpaRepository.saveAll(entities);

        return savedEntities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}