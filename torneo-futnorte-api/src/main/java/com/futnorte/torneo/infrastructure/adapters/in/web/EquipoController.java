package com.futnorte.torneo.infrastructure.adapters.in.web;

import com.futnorte.torneo.domain.entities.Equipo;
import com.futnorte.torneo.domain.ports.in.EquipoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {
    
    private final EquipoUseCase equipoUseCase;
    
    public EquipoController(EquipoUseCase equipoUseCase) {
        this.equipoUseCase = equipoUseCase;
    }
    
    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        try {
            Equipo equipoCreado = equipoUseCase.crearEquipo(equipo);
            return new ResponseEntity<>(equipoCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> buscarEquipoPorId(@PathVariable Long id) {
        try {
            Equipo equipo = equipoUseCase.buscarEquipoPorId(id);
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Equipo>> buscarTodosLosEquipos() {
        List<Equipo> equipos = equipoUseCase.buscarTodosLosEquipos();
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        try {
            Equipo equipoActualizado = equipoUseCase.actualizarEquipo(id, equipo);
            return new ResponseEntity<>(equipoActualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        try {
            equipoUseCase.eliminarEquipo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Equipo>> buscarEquiposPorTorneo(@PathVariable Long torneoId) {
        List<Equipo> equipos = equipoUseCase.buscarEquiposPorTorneo(torneoId);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

}