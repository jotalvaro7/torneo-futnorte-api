package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Jugador;
import org.springframework.stereotype.Component;

@Component
public class JugadorMapper {
    
    public JugadorEntity toEntity(Jugador jugador) {
        if (jugador == null) {
            return null;
        }
        
        JugadorEntity entity = new JugadorEntity();
        entity.setId(jugador.getId());
        entity.setNombre(jugador.getNombre());
        entity.setApellido(jugador.getApellido());
        entity.setIdentificacion(jugador.getIdentificacion());
        entity.setNacionalidad(jugador.getNacionalidad());
        entity.setEquipoId(jugador.getEquipoId());
        entity.setNumeroGoles(jugador.getNumeroGoles());
        
        return entity;
    }
    
    public Jugador toDomain(JugadorEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Jugador jugador = new Jugador();
        jugador.setId(entity.getId());
        jugador.setNombre(entity.getNombre());
        jugador.setApellido(entity.getApellido());
        jugador.setIdentificacion(entity.getIdentificacion());
        jugador.setNacionalidad(entity.getNacionalidad());
        jugador.setEquipoId(entity.getEquipoId());
        jugador.setNumeroGoles(entity.getNumeroGoles());
        
        return jugador;
    }
}