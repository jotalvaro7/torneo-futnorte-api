package com.futnorte.torneo.domain.ports.out;

import com.futnorte.torneo.domain.entities.Equipo;

import java.util.List;
import java.util.Optional;

public interface EquipoRepositoryPort {
    
    Equipo guardar(Equipo equipo);
    
    Optional<Equipo> buscarPorId(Long id);
    
    List<Equipo> buscarTodos();
    
    List<Equipo> buscarPorTorneoId(Long torneoId);
    
    boolean existePorId(Long id);
    
    boolean existePorNombreYTorneoId(String nombre, Long torneoId);
    
    void eliminarPorId(Long id);

}