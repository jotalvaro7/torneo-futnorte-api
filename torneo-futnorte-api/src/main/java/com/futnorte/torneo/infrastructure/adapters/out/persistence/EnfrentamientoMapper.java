package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import org.springframework.stereotype.Component;

@Component
public class EnfrentamientoMapper {
    
    public EnfrentamientoEntity toEntity(Enfrentamiento enfrentamiento) {
        if (enfrentamiento == null) {
            return null;
        }
        
        EnfrentamientoEntity entity = new EnfrentamientoEntity();
        entity.setId(enfrentamiento.getId());
        entity.setTorneoId(enfrentamiento.getTorneoId());
        entity.setEquipoLocalId(enfrentamiento.getEquipoLocalId());
        entity.setEquipoVisitanteId(enfrentamiento.getEquipoVisitanteId());
        entity.setFechaHora(enfrentamiento.getFechaHora());
        entity.setCancha(enfrentamiento.getCancha());
        entity.setEstado(enfrentamiento.getEstado());
        entity.setGolesLocal(enfrentamiento.getGolesLocal());
        entity.setGolesVisitante(enfrentamiento.getGolesVisitante());
        
        return entity;
    }
    
    public Enfrentamiento toDomain(EnfrentamientoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Enfrentamiento enfrentamiento = new Enfrentamiento();
        enfrentamiento.setId(entity.getId());
        enfrentamiento.setTorneoId(entity.getTorneoId());
        enfrentamiento.setEquipoLocalId(entity.getEquipoLocalId());
        enfrentamiento.setEquipoVisitanteId(entity.getEquipoVisitanteId());
        enfrentamiento.setFechaHora(entity.getFechaHora());
        enfrentamiento.setCancha(entity.getCancha());
        enfrentamiento.setEstado(entity.getEstado());
        enfrentamiento.setGolesLocal(entity.getGolesLocal());
        enfrentamiento.setGolesVisitante(entity.getGolesVisitante());
        
        return enfrentamiento;
    }
}