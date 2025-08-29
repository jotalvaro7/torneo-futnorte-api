package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.domain.ports.in.TorneoUseCase;
import com.futnorte.torneo.domain.ports.out.TorneoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TorneoService implements TorneoUseCase {
    
    private final TorneoRepositoryPort torneoRepository;
    
    public TorneoService(TorneoRepositoryPort torneoRepository) {
        this.torneoRepository = torneoRepository;
    }
    
    @Override
    public Torneo crearTorneo(Torneo torneo) {
        if (torneo.getNombre() == null || torneo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del torneo es obligatorio");
        }
        
        Optional<Torneo> torneoExistente = torneoRepository.findByNombre(torneo.getNombre());
        if (torneoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un torneo con ese nombre");
        }
        
        return torneoRepository.save(torneo);
    }
    
    @Override
    public Optional<Torneo> obtenerTorneo(Long id) {
        return torneoRepository.findById(id);
    }
    
    @Override
    public List<Torneo> obtenerTodosTorneos() {
        return torneoRepository.findAll();
    }
    
    @Override
    public Torneo actualizarTorneo(Long id, Torneo torneo) {
        return torneoRepository.findById(id)
                .map(torneoExistente -> {
                    // Solo actualizar campos que no son null
                    if (torneo.getNombre() != null) {
                        torneoExistente.setNombre(torneo.getNombre());
                    }
                    if (torneo.getDescripcion() != null) {
                        torneoExistente.setDescripcion(torneo.getDescripcion());
                    }
                    if (torneo.getFechaInicio() != null) {
                        torneoExistente.setFechaInicio(torneo.getFechaInicio());
                    }
                    if (torneo.getFechaFin() != null) {
                        torneoExistente.setFechaFin(torneo.getFechaFin());
                    }
                    // No permitir cambio manual de estado o equiposIds via PUT
                    // Estos se manejan por endpoints específicos
                    return torneoRepository.save(torneoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + id));
    }
    
    @Override
    public Torneo cancelarTorneo(Long id) {
        return torneoRepository.findById(id)
                .map(torneo -> {
                    torneo.cancelarTorneo();
                    return torneoRepository.save(torneo);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + id));
    }
    
    @Override
    public void eliminarTorneo(Long id) {
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + id));
        
        torneo.validarEliminacion();
        torneoRepository.deleteById(id);
    }
    
    @Override
    public Torneo iniciarTorneo(Long id) {
        return torneoRepository.findById(id)
                .map(torneo -> {
                    torneo.iniciarTorneo();
                    return torneoRepository.save(torneo);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + id));
    }
    
    @Override
    public Torneo finalizarTorneo(Long id) {
        return torneoRepository.findById(id)
                .map(torneo -> {
                    torneo.finalizarTorneo();
                    return torneoRepository.save(torneo);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + id));
    }
    
    @Override
    public Torneo agregarEquipo(Long torneoId, Long equipoId) {
        return torneoRepository.findById(torneoId)
                .map(torneo -> {
                    torneo.agregarEquipo(equipoId);
                    return torneoRepository.save(torneo);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + torneoId));
    }
    
    @Override
    public Torneo removerEquipo(Long torneoId, Long equipoId) {
        return torneoRepository.findById(torneoId)
                .map(torneo -> {
                    torneo.removerEquipo(equipoId);
                    return torneoRepository.save(torneo);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID: " + torneoId));
    }
}