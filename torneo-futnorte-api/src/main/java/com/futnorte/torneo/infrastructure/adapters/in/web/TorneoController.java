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
        Torneo torneoCreado = torneoUseCase.crearTorneo(torneo);
        return new ResponseEntity<>(torneoCreado, HttpStatus.CREATED);
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
        Torneo torneoActualizado = torneoUseCase.actualizarTorneo(id, torneo);
        return new ResponseEntity<>(torneoActualizado, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Torneo> iniciarTorneo(@PathVariable Long id) {
        Torneo torneo = torneoUseCase.iniciarTorneo(id);
        return new ResponseEntity<>(torneo, HttpStatus.OK);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Torneo> cancelarTorneo(@PathVariable Long id) {
        Torneo torneo = torneoUseCase.cancelarTorneo(id);
        return new ResponseEntity<>(torneo, HttpStatus.OK);
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Torneo> finalizarTorneo(@PathVariable Long id) {
        Torneo torneo = torneoUseCase.finalizarTorneo(id);
        return new ResponseEntity<>(torneo, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTorneo(@PathVariable Long id) {
        torneoUseCase.eliminarTorneo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}