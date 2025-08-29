package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.Equipo;

import java.util.List;

public interface EquipoUseCase {
    
    Equipo crearEquipo(Equipo equipo);
    
    Equipo actualizarEquipo(Long id, Equipo equipo);
    
    Equipo buscarEquipoPorId(Long id);
    
    List<Equipo> buscarTodosLosEquipos();
    
    List<Equipo> buscarEquiposPorTorneo(Long torneoId);
    
    void eliminarEquipo(Long id);
}