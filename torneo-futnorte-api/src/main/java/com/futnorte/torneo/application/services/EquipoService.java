package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.ports.in.EquipoUseCase;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService implements EquipoUseCase {
    
    private final EquipoRepositoryPort equipoRepositoryPort;
    
    public EquipoService(EquipoRepositoryPort equipoRepositoryPort) {
        this.equipoRepositoryPort = equipoRepositoryPort;
    }
    
    @Override
    public Equipo crearEquipo(Equipo equipo) {
        equipo.validarEquipo();
        
        if (equipoRepositoryPort.existePorNombreYTorneoId(equipo.getNombre(), equipo.getTorneoId())) {
            throw new IllegalArgumentException("Ya existe un equipo con ese nombre en el torneo");
        }
        
        return equipoRepositoryPort.guardar(equipo);
    }
    
    @Override
    public Equipo actualizarEquipo(Long id, Equipo equipo) {
        if (!equipoRepositoryPort.existePorId(id)) {
            throw new IllegalArgumentException("El equipo no existe");
        }
        
        Equipo equipoExistente = buscarEquipoPorId(id);

        if (equipo.getNombre() != null) {
            equipoExistente.setNombre(equipo.getNombre());
        }
        if (equipo.getEntrenador() != null) {
            equipoExistente.setEntrenador(equipo.getEntrenador());
        }
        if (equipo.getTorneoId() != null) {
            equipoExistente.setTorneoId(equipo.getTorneoId());
        }

        Equipo equipoNuevo = new Equipo();
        
        if (equipo.getPuntos() != equipoNuevo.getPuntos()) {
            equipoExistente.setPuntos(equipo.getPuntos());
        }
        if (equipo.getPartidosJugados() != equipoNuevo.getPartidosJugados()) {
            equipoExistente.setPartidosJugados(equipo.getPartidosJugados());
        }
        if (equipo.getPartidosGanados() != equipoNuevo.getPartidosGanados()) {
            equipoExistente.setPartidosGanados(equipo.getPartidosGanados());
        }
        if (equipo.getPartidosEmpatados() != equipoNuevo.getPartidosEmpatados()) {
            equipoExistente.setPartidosEmpatados(equipo.getPartidosEmpatados());
        }
        if (equipo.getPartidosPerdidos() != equipoNuevo.getPartidosPerdidos()) {
            equipoExistente.setPartidosPerdidos(equipo.getPartidosPerdidos());
        }
        if (equipo.getGolesAFavor() != equipoNuevo.getGolesAFavor()) {
            equipoExistente.setGolesAFavor(equipo.getGolesAFavor());
        }
        if (equipo.getGolesEnContra() != equipoNuevo.getGolesEnContra()) {
            equipoExistente.setGolesEnContra(equipo.getGolesEnContra());
        }
        
        equipoExistente.validarEquipo();
        
        return equipoRepositoryPort.guardar(equipoExistente);
    }
    
    @Override
    public Equipo buscarEquipoPorId(Long id) {
        return equipoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El equipo no existe"));
    }
    
    @Override
    public List<Equipo> buscarTodosLosEquipos() {
        return equipoRepositoryPort.buscarTodos();
    }
    
    @Override
    public List<Equipo> buscarEquiposPorTorneo(Long torneoId) {
        return equipoRepositoryPort.buscarPorTorneoId(torneoId);
    }
    
    @Override
    public void eliminarEquipo(Long id) {
        if (!equipoRepositoryPort.existePorId(id)) {
            throw new IllegalArgumentException("El equipo no existe");
        }
        equipoRepositoryPort.eliminarPorId(id);
    }
    

}