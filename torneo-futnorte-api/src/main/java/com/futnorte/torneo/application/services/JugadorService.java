package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.ports.in.JugadorUseCase;
import com.futnorte.torneo.domain.ports.out.JugadorRepositoryPort;
import com.futnorte.torneo.domain.ports.out.EquipoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService implements JugadorUseCase {
    
    private final JugadorRepositoryPort jugadorRepositoryPort;
    private final EquipoRepositoryPort equipoRepositoryPort;
    
    public JugadorService(JugadorRepositoryPort jugadorRepositoryPort, EquipoRepositoryPort equipoRepositoryPort) {
        this.jugadorRepositoryPort = jugadorRepositoryPort;
        this.equipoRepositoryPort = equipoRepositoryPort;
    }
    
    @Override
    public Jugador crearJugador(Jugador jugador) {
        jugador.validarJugador();
        
        if (!equipoRepositoryPort.existePorId(jugador.getEquipoId())) {
            throw new IllegalArgumentException("El equipo especificado no existe");
        }
        
        if (jugadorRepositoryPort.existePorIdentificacion(jugador.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un jugador con esa identificación");
        }
        
        return jugadorRepositoryPort.guardar(jugador);
    }
    
    @Override
    public Jugador actualizarJugador(Long id, Jugador jugador) {
        if (!jugadorRepositoryPort.existePorId(id)) {
            throw new IllegalArgumentException("El jugador no existe");
        }
        
        Jugador jugadorExistente = buscarJugadorPorId(id);
        
        if (jugador.getNombre() != null) {
            jugadorExistente.setNombre(jugador.getNombre());
        }
        if (jugador.getApellido() != null) {
            jugadorExistente.setApellido(jugador.getApellido());
        }
        if (jugador.getIdentificacion() != null && !jugador.getIdentificacion().equals(jugadorExistente.getIdentificacion())) {
            if (jugadorRepositoryPort.existePorIdentificacion(jugador.getIdentificacion())) {
                throw new IllegalArgumentException("Ya existe un jugador con esa identificación");
            }
            jugadorExistente.setIdentificacion(jugador.getIdentificacion());
        }
        if (jugador.getEquipoId() != null) {
            if (!equipoRepositoryPort.existePorId(jugador.getEquipoId())) {
                throw new IllegalArgumentException("El equipo especificado no existe");
            }
            jugadorExistente.setEquipoId(jugador.getEquipoId());
        }
        
        Jugador jugadorNuevo = new Jugador();
        if (jugador.getNumeroGoles() != jugadorNuevo.getNumeroGoles()) {
            jugadorExistente.setNumeroGoles(jugador.getNumeroGoles());
        }
        
        jugadorExistente.validarJugador();
        
        return jugadorRepositoryPort.guardar(jugadorExistente);
    }
    
    @Override
    public Jugador buscarJugadorPorId(Long id) {
        return jugadorRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El jugador no existe"));
    }
    
    @Override
    public List<Jugador> buscarTodosLosJugadores() {
        return jugadorRepositoryPort.buscarTodos();
    }
    
    @Override
    public List<Jugador> buscarJugadoresPorEquipo(Long equipoId) {
        return jugadorRepositoryPort.buscarPorEquipoId(equipoId);
    }
    
    @Override
    public Jugador buscarJugadorPorIdentificacion(String identificacion) {
        return jugadorRepositoryPort.buscarPorIdentificacion(identificacion)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró jugador con esa identificación"));
    }
    
    @Override
    public void eliminarJugador(Long id) {
        if (!jugadorRepositoryPort.existePorId(id)) {
            throw new IllegalArgumentException("El jugador no existe");
        }
        jugadorRepositoryPort.eliminarPorId(id);
    }
    
    @Override
    public void eliminarJugadoresPorEquipo(Long equipoId) {
        jugadorRepositoryPort.eliminarPorEquipoId(equipoId);
    }
    
    @Override
    public Jugador cambiarEquipo(Long jugadorId, Long nuevoEquipoId) {
        if (!equipoRepositoryPort.existePorId(nuevoEquipoId)) {
            throw new IllegalArgumentException("El nuevo equipo especificado no existe");
        }
        
        Jugador jugador = buscarJugadorPorId(jugadorId);
        jugador.setEquipoId(nuevoEquipoId);
        
        return jugadorRepositoryPort.guardar(jugador);
    }
}