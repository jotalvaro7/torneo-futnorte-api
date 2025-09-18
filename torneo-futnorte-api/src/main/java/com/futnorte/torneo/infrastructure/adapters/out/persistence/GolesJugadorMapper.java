package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.GolesJugador;
import org.springframework.stereotype.Component;

@Component
public class GolesJugadorMapper {

    public GolesJugadorEntity toEntity(GolesJugador golesJugador) {
        if (golesJugador == null) {
            return null;
        }

        GolesJugadorEntity entity = new GolesJugadorEntity();
        entity.setId(golesJugador.getId());
        entity.setEnfrentamientoId(golesJugador.getEnfrentamientoId());
        entity.setJugadorId(golesJugador.getJugadorId());
        entity.setCantidadGoles(golesJugador.getCantidadGoles());

        return entity;
    }

    public GolesJugador toDomain(GolesJugadorEntity entity) {
        if (entity == null) {
            return null;
        }

        GolesJugador golesJugador = new GolesJugador();
        golesJugador.setId(entity.getId());
        golesJugador.setEnfrentamientoId(entity.getEnfrentamientoId());
        golesJugador.setJugadorId(entity.getJugadorId());
        golesJugador.setCantidadGoles(entity.getCantidadGoles());

        return golesJugador;
    }
}