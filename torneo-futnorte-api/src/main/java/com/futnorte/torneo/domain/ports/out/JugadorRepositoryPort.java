package com.futnorte.torneo.domain.ports.out;

import com.futnorte.torneo.domain.entities.Jugador;

import java.util.List;
import java.util.Optional;

public interface JugadorRepositoryPort {
    
    Jugador guardar(Jugador jugador);
    
    Optional<Jugador> buscarPorId(Long id);
    
    List<Jugador> buscarTodos();
    
    List<Jugador> buscarPorEquipoId(Long equipoId);
    
    Optional<Jugador> buscarPorIdentificacion(String identificacion);
    
    boolean existePorId(Long id);
    
    boolean existePorIdentificacion(String identificacion);
    
    void eliminarPorId(Long id);
    
    void eliminarPorEquipoId(Long equipoId);
}