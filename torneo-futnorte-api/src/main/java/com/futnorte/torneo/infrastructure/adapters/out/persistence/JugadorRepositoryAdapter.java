package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.ports.out.JugadorRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JugadorRepositoryAdapter implements JugadorRepositoryPort {
    
    private final JugadorJpaRepository jugadorJpaRepository;
    private final JugadorMapper jugadorMapper;
    
    public JugadorRepositoryAdapter(JugadorJpaRepository jugadorJpaRepository, JugadorMapper jugadorMapper) {
        this.jugadorJpaRepository = jugadorJpaRepository;
        this.jugadorMapper = jugadorMapper;
    }
    
    @Override
    public Jugador guardar(Jugador jugador) {
        JugadorEntity entity = jugadorMapper.toEntity(jugador);
        JugadorEntity savedEntity = jugadorJpaRepository.save(entity);
        return jugadorMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Jugador> buscarPorId(Long id) {
        return jugadorJpaRepository.findById(id)
                .map(jugadorMapper::toDomain);
    }
    
    @Override
    public List<Jugador> buscarTodos() {
        return jugadorJpaRepository.findAll()
                .stream()
                .map(jugadorMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Jugador> buscarPorEquipoId(Long equipoId) {
        return jugadorJpaRepository.findByEquipoId(equipoId)
                .stream()
                .map(jugadorMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Jugador> buscarPorIdentificacion(String identificacion) {
        return jugadorJpaRepository.findByIdentificacion(identificacion)
                .map(jugadorMapper::toDomain);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return jugadorJpaRepository.existsById(id);
    }
    
    @Override
    public boolean existePorIdentificacion(String identificacion) {
        return jugadorJpaRepository.existsByIdentificacion(identificacion);
    }
    
    @Override
    public void eliminarPorId(Long id) {
        jugadorJpaRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public void eliminarPorEquipoId(Long equipoId) {
        jugadorJpaRepository.deleteByEquipoId(equipoId);
    }
}