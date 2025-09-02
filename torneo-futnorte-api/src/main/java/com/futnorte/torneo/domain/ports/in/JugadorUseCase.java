package com.futnorte.torneo.domain.ports.in;

import com.futnorte.torneo.domain.entities.Jugador;

import java.util.List;

public interface JugadorUseCase {
    
    Jugador crearJugador(Jugador jugador);
    
    Jugador actualizarJugador(Long id, Jugador jugador);
    
    Jugador buscarJugadorPorId(Long id);
    
    List<Jugador> buscarTodosLosJugadores();
    
    List<Jugador> buscarJugadoresPorEquipo(Long equipoId);
    
    Jugador buscarJugadorPorIdentificacion(String identificacion);
    
    void eliminarJugador(Long id);
    
    void eliminarJugadoresPorEquipo(Long equipoId);
    
    Jugador cambiarEquipo(Long jugadorId, Long nuevoEquipoId);
    
    List<Jugador> obtenerGoleadoresPorTorneo(Long torneoId);
}