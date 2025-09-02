package com.futnorte.torneo.infrastructure.adapters.in.web;

import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.domain.ports.in.EquipoUseCase;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.ActualizarEnfrentamientoRequest;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.CrearEnfrentamientoRequest;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.EnfrentamientoResponse;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.RegistrarGolesJugadorRequest;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.RegistrarResultadoRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enfrentamientos")
@CrossOrigin(origins = "*")
public class EnfrentamientoController {
    
    private final EnfrentamientoUseCase enfrentamientoUseCase;
    private final EquipoUseCase equipoUseCase;
    
    public EnfrentamientoController(EnfrentamientoUseCase enfrentamientoUseCase, EquipoUseCase equipoUseCase) {
        this.enfrentamientoUseCase = enfrentamientoUseCase;
        this.equipoUseCase = equipoUseCase;
    }
    
    @PostMapping
    public ResponseEntity<EnfrentamientoResponse> crearEnfrentamiento(@Valid @RequestBody CrearEnfrentamientoRequest request) {
        Enfrentamiento enfrentamiento = enfrentamientoUseCase.crearEnfrentamiento(
                request.getTorneoId(),
                request.getEquipoLocalId(),
                request.getEquipoVisitanteId(),
                request.getFechaHora(),
                request.getCancha()
        );
        EnfrentamientoResponse response = toResponse(enfrentamiento);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EnfrentamientoResponse> obtenerEnfrentamiento(@PathVariable Long id) {
        return enfrentamientoUseCase.obtenerEnfrentamientoPorId(id)
                .map(enfrentamiento -> new ResponseEntity<>(toResponse(enfrentamiento), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<EnfrentamientoResponse>> obtenerEnfrentamientosPorTorneo(@PathVariable Long torneoId) {
        List<Enfrentamiento> enfrentamientos = enfrentamientoUseCase.obtenerEnfrentamientosPorTorneo(torneoId);
        List<EnfrentamientoResponse> responses = enfrentamientos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<EnfrentamientoResponse>> obtenerEnfrentamientosPorEquipo(@PathVariable Long equipoId) {
        List<Enfrentamiento> enfrentamientos = enfrentamientoUseCase.obtenerEnfrentamientosPorEquipo(equipoId);
        List<EnfrentamientoResponse> responses = enfrentamientos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    @GetMapping("/fecha")
    public ResponseEntity<List<EnfrentamientoResponse>> obtenerEnfrentamientosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Enfrentamiento> enfrentamientos = enfrentamientoUseCase.obtenerEnfrentamientosPorFecha(fechaInicio, fechaFin);
        List<EnfrentamientoResponse> responses = enfrentamientos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EnfrentamientoResponse> actualizarEnfrentamiento(
            @PathVariable Long id, 
            @Valid @RequestBody ActualizarEnfrentamientoRequest request) {
        Enfrentamiento enfrentamiento = enfrentamientoUseCase.actualizarEnfrentamiento(
                id, 
                request.getFechaHora(), 
                request.getCancha()
        );
        EnfrentamientoResponse response = toResponse(enfrentamiento);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/resultado")
    public ResponseEntity<EnfrentamientoResponse> registrarResultado(
            @PathVariable Long id, 
            @Valid @RequestBody RegistrarResultadoRequest request) {
        Enfrentamiento enfrentamiento = enfrentamientoUseCase.registrarResultado(
                id, 
                request.getGolesLocal(), 
                request.getGolesVisitante()
        );
        EnfrentamientoResponse response = toResponse(enfrentamiento);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/goles-jugador")
    public ResponseEntity<Void> registrarGolesJugador(
            @PathVariable Long id, 
            @Valid @RequestBody RegistrarGolesJugadorRequest request) {
        enfrentamientoUseCase.registrarGolesJugador(
                id, 
                request.getJugadorId(), 
                request.getCantidadGoles()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<EnfrentamientoResponse> cancelarEnfrentamiento(@PathVariable Long id) {
        Enfrentamiento enfrentamiento = enfrentamientoUseCase.cancelarEnfrentamiento(id);
        EnfrentamientoResponse response = toResponse(enfrentamiento);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnfrentamiento(@PathVariable Long id) {
        enfrentamientoUseCase.eliminarEnfrentamiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private EnfrentamientoResponse toResponse(Enfrentamiento enfrentamiento) {
        String equipoLocalNombre = equipoUseCase.buscarEquipoPorId(enfrentamiento.getEquipoLocalId()).getNombre();
        String equipoVisitanteNombre = equipoUseCase.buscarEquipoPorId(enfrentamiento.getEquipoVisitanteId()).getNombre();
        
        return new EnfrentamientoResponse(
                enfrentamiento.getId(),
                enfrentamiento.getTorneoId(),
                equipoLocalNombre,
                equipoVisitanteNombre,
                enfrentamiento.getFechaHora(),
                enfrentamiento.getCancha(),
                enfrentamiento.getEstado(),
                enfrentamiento.getGolesLocal(),
                enfrentamiento.getGolesVisitante()
        );
    }
}