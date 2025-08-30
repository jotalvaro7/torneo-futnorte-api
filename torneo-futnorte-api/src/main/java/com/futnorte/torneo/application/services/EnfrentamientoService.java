package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.domain.ports.out.EnfrentamientoRepositoryPort;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import com.futnorte.torneo.domain.ports.out.TorneoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnfrentamientoService implements EnfrentamientoUseCase {
    
    private final EnfrentamientoRepositoryPort enfrentamientoRepository;
    private final EquipoRepositoryPort equipoRepository;
    private final TorneoRepositoryPort torneoRepository;
    
    public EnfrentamientoService(EnfrentamientoRepositoryPort enfrentamientoRepository,
                               EquipoRepositoryPort equipoRepository,
                               TorneoRepositoryPort torneoRepository) {
        this.enfrentamientoRepository = enfrentamientoRepository;
        this.equipoRepository = equipoRepository;
        this.torneoRepository = torneoRepository;
    }
    
    @Override
    public Enfrentamiento crearEnfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId,
                                            LocalDateTime fechaHora, String cancha) {
        
        Torneo torneo = torneoRepository.findById(torneoId)
                .orElseThrow(() -> new IllegalArgumentException("El torneo no existe"));
        
        Equipo equipoLocal = equipoRepository.buscarPorId(equipoLocalId)
                .orElseThrow(() -> new IllegalArgumentException("El equipo local no existe"));
        
        Equipo equipoVisitante = equipoRepository.buscarPorId(equipoVisitanteId)
                .orElseThrow(() -> new IllegalArgumentException("El equipo visitante no existe"));
        
        if (!equipoLocal.getTorneoId().equals(torneoId) || 
            !equipoVisitante.getTorneoId().equals(torneoId)) {
            throw new IllegalArgumentException("Ambos equipos deben pertenecer al torneo especificado");
        }
        
        Enfrentamiento enfrentamiento = new Enfrentamiento(torneoId, equipoLocalId, equipoVisitanteId, 
                                                          fechaHora, cancha);
        enfrentamiento.validarEnfrentamiento();
        
        return enfrentamientoRepository.save(enfrentamiento);
    }
    
    @Override
    public Enfrentamiento actualizarEnfrentamiento(Long enfrentamientoId, LocalDateTime fechaHora, String cancha) {
        Enfrentamiento enfrentamiento = enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new IllegalArgumentException("El enfrentamiento no existe"));
        
        enfrentamiento.actualizarDetalles(fechaHora, cancha);
        
        return enfrentamientoRepository.save(enfrentamiento);
    }
    
    @Override
    public Enfrentamiento registrarResultado(Long enfrentamientoId, int golesLocal, int golesVisitante) {
        Enfrentamiento enfrentamiento = enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new IllegalArgumentException("El enfrentamiento no existe"));
        
        Equipo equipoLocal = equipoRepository.buscarPorId(enfrentamiento.getEquipoLocalId())
                .orElseThrow(() -> new IllegalArgumentException("El equipo local no existe"));
        
        Equipo equipoVisitante = equipoRepository.buscarPorId(enfrentamiento.getEquipoVisitanteId())
                .orElseThrow(() -> new IllegalArgumentException("El equipo visitante no existe"));
        
        enfrentamiento.registrarResultado(golesLocal, golesVisitante, equipoLocal, equipoVisitante);
        
        equipoRepository.guardar(equipoLocal);
        equipoRepository.guardar(equipoVisitante);
        
        return enfrentamientoRepository.save(enfrentamiento);
    }
    
    @Override
    public Enfrentamiento cancelarEnfrentamiento(Long enfrentamientoId) {
        Enfrentamiento enfrentamiento = enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new IllegalArgumentException("El enfrentamiento no existe"));
        
        enfrentamiento.cancelar();
        
        return enfrentamientoRepository.save(enfrentamiento);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfrentamiento> obtenerEnfrentamientoPorId(Long enfrentamientoId) {
        return enfrentamientoRepository.findById(enfrentamientoId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Enfrentamiento> obtenerEnfrentamientosPorTorneo(Long torneoId) {
        return enfrentamientoRepository.findByTorneoId(torneoId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Enfrentamiento> obtenerEnfrentamientosPorEquipo(Long equipoId) {
        return enfrentamientoRepository.findByEquipoLocalIdOrEquipoVisitanteId(equipoId, equipoId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Enfrentamiento> obtenerEnfrentamientosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return enfrentamientoRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
    }
    
    @Override
    public void eliminarEnfrentamiento(Long enfrentamientoId) {
        Enfrentamiento enfrentamiento = enfrentamientoRepository.findById(enfrentamientoId)
                .orElseThrow(() -> new IllegalArgumentException("El enfrentamiento no existe"));
        
        if (enfrentamiento.estaFinalizado()) {
            throw new IllegalStateException("No se puede eliminar un enfrentamiento finalizado");
        }
        
        enfrentamientoRepository.deleteById(enfrentamientoId);
    }
}