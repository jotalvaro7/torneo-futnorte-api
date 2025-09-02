package com.futnorte.torneo.application.services;

import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.exceptions.DuplicateEntityException;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
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
            throw new EntityNotFoundException("Equipo", jugador.getEquipoId());
        }
        
        if (jugadorRepositoryPort.existePorIdentificacion(jugador.getIdentificacion())) {
            throw new DuplicateEntityException("Ya existe un jugador con esa identificación");
        }
        
        return jugadorRepositoryPort.guardar(jugador);
    }
    
    @Override
    public Jugador actualizarJugador(Long id, Jugador jugador) {
        if (!jugadorRepositoryPort.existePorId(id)) {
            throw new EntityNotFoundException("Jugador", id);
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
                throw new DuplicateEntityException("Ya existe un jugador con esa identificación");
            }
            jugadorExistente.setIdentificacion(jugador.getIdentificacion());
        }
        if (jugador.getEquipoId() != null) {
            if (!equipoRepositoryPort.existePorId(jugador.getEquipoId())) {
                throw new EntityNotFoundException("Equipo", jugador.getEquipoId());
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
                .orElseThrow(() -> new EntityNotFoundException("Jugador", id));
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
                .orElseThrow(() -> new EntityNotFoundException("Jugador", identificacion));
    }
    
    @Override
    public void eliminarJugador(Long id) {
        if (!jugadorRepositoryPort.existePorId(id)) {
            throw new EntityNotFoundException("Jugador", id);
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
            throw new EntityNotFoundException("Equipo", nuevoEquipoId);
        }
        
        Jugador jugador = buscarJugadorPorId(jugadorId);
        jugador.setEquipoId(nuevoEquipoId);
        
        return jugadorRepositoryPort.guardar(jugador);
    }
    
    @Override
    public List<Jugador> obtenerGoleadoresPorTorneo(Long torneoId) {
        return jugadorRepositoryPort.buscarGoleadoresPorTorneo(torneoId);
    }
}