package com.futnorte.torneo.infrastructure.adapters.in.web;

import com.futnorte.torneo.domain.entities.Torneo;
import com.futnorte.torneo.domain.ports.in.TorneoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/torneos")
@CrossOrigin(origins = "*")
public class TorneoController {
    
    private final TorneoUseCase torneoUseCase;
    
    public TorneoController(TorneoUseCase torneoUseCase) {
        this.torneoUseCase = torneoUseCase;
    }
    
    @PostMapping
    public ResponseEntity<Torneo> crearTorneo(@RequestBody Torneo torneo) {
        try {
            Torneo torneoCreado = torneoUseCase.crearTorneo(torneo);
            return new ResponseEntity<>(torneoCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Torneo> obtenerTorneo(@PathVariable Long id) {
        return torneoUseCase.obtenerTorneo(id)
                .map(torneo -> new ResponseEntity<>(torneo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<Torneo>> obtenerTodosTorneos() {
        List<Torneo> torneos = torneoUseCase.obtenerTodosTorneos();
        return new ResponseEntity<>(torneos, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Torneo> actualizarTorneo(@PathVariable Long id, @RequestBody Torneo torneo) {
        try {
            Torneo torneoActualizado = torneoUseCase.actualizarTorneo(id, torneo);
            return new ResponseEntity<>(torneoActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Torneo> iniciarTorneo(@PathVariable Long id) {
        try {
            Torneo torneo = torneoUseCase.iniciarTorneo(id);
            return new ResponseEntity<>(torneo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Torneo> cancelarTorneo(@PathVariable Long id) {
        try {
            Torneo torneo = torneoUseCase.cancelarTorneo(id);
            return new ResponseEntity<>(torneo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Torneo> finalizarTorneo(@PathVariable Long id) {
        try {
            Torneo torneo = torneoUseCase.finalizarTorneo(id);
            return new ResponseEntity<>(torneo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTorneo(@PathVariable Long id) {
        try {
            torneoUseCase.eliminarTorneo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    @PostMapping("/{torneoId}/equipos/{equipoId}")
    public ResponseEntity<Torneo> agregarEquipo(@PathVariable Long torneoId, @PathVariable Long equipoId) {
        try {
            Torneo torneo = torneoUseCase.agregarEquipo(torneoId, equipoId);
            return new ResponseEntity<>(torneo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{torneoId}/equipos/{equipoId}")
    public ResponseEntity<Torneo> removerEquipo(@PathVariable Long torneoId, @PathVariable Long equipoId) {
        try {
            Torneo torneo = torneoUseCase.removerEquipo(torneoId, equipoId);
            return new ResponseEntity<>(torneo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}