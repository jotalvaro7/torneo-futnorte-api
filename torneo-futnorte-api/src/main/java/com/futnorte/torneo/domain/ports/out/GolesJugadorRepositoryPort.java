package com.futnorte.torneo.domain.ports.out;

import com.futnorte.torneo.domain.entities.GolesJugador;

import java.util.List;

public interface GolesJugadorRepositoryPort {

    GolesJugador save(GolesJugador golesJugador);

    List<GolesJugador> findByEnfrentamientoId(Long enfrentamientoId);

    void deleteByEnfrentamientoId(Long enfrentamientoId);

    List<GolesJugador> saveAll(List<GolesJugador> golesJugadores);
}