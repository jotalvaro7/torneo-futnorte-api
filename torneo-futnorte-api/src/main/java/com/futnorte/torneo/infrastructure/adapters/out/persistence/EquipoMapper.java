package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Equipo;
import org.springframework.stereotype.Component;

@Component
public class EquipoMapper {
    
    public EquipoEntity toEntity(Equipo equipo) {
        if (equipo == null) {
            return null;
        }
        
        EquipoEntity entity = new EquipoEntity();
        entity.setId(equipo.getId());
        entity.setNombre(equipo.getNombre());
        entity.setEntrenador(equipo.getEntrenador());
        entity.setTorneoId(equipo.getTorneoId());
        entity.setPuntos(equipo.getPuntos());
        entity.setPartidosJugados(equipo.getPartidosJugados());
        entity.setPartidosGanados(equipo.getPartidosGanados());
        entity.setPartidosEmpatados(equipo.getPartidosEmpatados());
        entity.setPartidosPerdidos(equipo.getPartidosPerdidos());
        entity.setGolesAFavor(equipo.getGolesAFavor());
        entity.setGolesEnContra(equipo.getGolesEnContra());
        
        return entity;
    }
    
    public Equipo toDomain(EquipoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Equipo equipo = new Equipo();
        equipo.setId(entity.getId());
        equipo.setNombre(entity.getNombre());
        equipo.setEntrenador(entity.getEntrenador());
        equipo.setTorneoId(entity.getTorneoId());
        equipo.setPuntos(entity.getPuntos());
        equipo.setPartidosJugados(entity.getPartidosJugados());
        equipo.setPartidosGanados(entity.getPartidosGanados());
        equipo.setPartidosEmpatados(entity.getPartidosEmpatados());
        equipo.setPartidosPerdidos(entity.getPartidosPerdidos());
        equipo.setGolesAFavor(entity.getGolesAFavor());
        equipo.setGolesEnContra(entity.getGolesEnContra());
        
        return equipo;
    }
}