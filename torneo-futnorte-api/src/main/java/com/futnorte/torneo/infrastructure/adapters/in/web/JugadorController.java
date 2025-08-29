package com.futnorte.torneo.infrastructure.adapters.in.web;

import com.futnorte.torneo.domain.entities.Jugador;
import com.futnorte.torneo.domain.ports.in.JugadorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {
    
    private final JugadorUseCase jugadorUseCase;
    
    public JugadorController(JugadorUseCase jugadorUseCase) {
        this.jugadorUseCase = jugadorUseCase;
    }
    
    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
        try {
            Jugador jugadorCreado = jugadorUseCase.crearJugador(jugador);
            return new ResponseEntity<>(jugadorCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Jugador> buscarJugadorPorId(@PathVariable Long id) {
        try {
            Jugador jugador = jugadorUseCase.buscarJugadorPorId(id);
            return new ResponseEntity<>(jugador, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Jugador>> buscarTodosLosJugadores() {
        List<Jugador> jugadores = jugadorUseCase.buscarTodosLosJugadores();
        return new ResponseEntity<>(jugadores, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizarJugador(@PathVariable Long id, @RequestBody Jugador jugador) {
        try {
            Jugador jugadorActualizado = jugadorUseCase.actualizarJugador(id, jugador);
            return new ResponseEntity<>(jugadorActualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Long id) {
        try {
            jugadorUseCase.eliminarJugador(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<Jugador>> buscarJugadoresPorEquipo(@PathVariable Long equipoId) {
        List<Jugador> jugadores = jugadorUseCase.buscarJugadoresPorEquipo(equipoId);
        return new ResponseEntity<>(jugadores, HttpStatus.OK);
    }
    
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<Jugador> buscarJugadorPorIdentificacion(@PathVariable String identificacion) {
        try {
            Jugador jugador = jugadorUseCase.buscarJugadorPorIdentificacion(identificacion);
            return new ResponseEntity<>(jugador, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/cambiar-equipo")
    public ResponseEntity<Jugador> cambiarEquipo(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        try {
            Long nuevoEquipoId = request.get("equipoId");
            Jugador jugadorActualizado = jugadorUseCase.cambiarEquipo(id, nuevoEquipoId);
            return new ResponseEntity<>(jugadorActualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}