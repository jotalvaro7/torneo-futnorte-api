package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.Torneo;

import java.util.List;
import java.util.Optional;

public interface TorneoUseCase {
    
    Torneo crearTorneo(Torneo torneo);
    
    Optional<Torneo> obtenerTorneo(Long id);
    
    List<Torneo> obtenerTodosTorneos();
    
    Torneo actualizarTorneo(Long id, Torneo torneo);
    
    Torneo iniciarTorneo(Long id);
    
    Torneo cancelarTorneo(Long id);
    
    Torneo finalizarTorneo(Long id);
    
    void eliminarTorneo(Long id);
    
    Torneo agregarEquipo(Long torneoId, Long equipoId);
    
    Torneo removerEquipo(Long torneoId, Long equipoId);
}