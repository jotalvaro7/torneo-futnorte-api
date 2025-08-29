package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EquipoRepositoryAdapter implements EquipoRepositoryPort {
    
    private final EquipoJpaRepository equipoJpaRepository;
    private final EquipoMapper equipoMapper;
    
    public EquipoRepositoryAdapter(EquipoJpaRepository equipoJpaRepository, EquipoMapper equipoMapper) {
        this.equipoJpaRepository = equipoJpaRepository;
        this.equipoMapper = equipoMapper;
    }
    
    @Override
    public Equipo guardar(Equipo equipo) {
        EquipoEntity entity = equipoMapper.toEntity(equipo);
        EquipoEntity savedEntity = equipoJpaRepository.save(entity);
        return equipoMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Equipo> buscarPorId(Long id) {
        return equipoJpaRepository.findById(id)
                .map(equipoMapper::toDomain);
    }
    
    @Override
    public List<Equipo> buscarTodos() {
        return equipoJpaRepository.findAll()
                .stream()
                .map(equipoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Equipo> buscarPorTorneoId(Long torneoId) {
        return equipoJpaRepository.findByTorneoIdOrderByPuntosDesc(torneoId)
                .stream()
                .map(equipoMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existePorId(Long id) {
        return equipoJpaRepository.existsById(id);
    }
    
    @Override
    public boolean existePorNombreYTorneoId(String nombre, Long torneoId) {
        return equipoJpaRepository.existsByNombreAndTorneoId(nombre, torneoId);
    }
    
    @Override
    public void eliminarPorId(Long id) {
        equipoJpaRepository.deleteById(id);
    }
    

}