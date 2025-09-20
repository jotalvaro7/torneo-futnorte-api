package com.futnorte.torneo.infrastructure.adapters.in.web;

import com.futnorte.torneo.application.commands.ActualizacionEnfrentamientoCommand;
import com.futnorte.torneo.application.handlers.ActualizarEnfrentamientoHandler;
import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.domain.ports.in.EquipoUseCase;
import com.futnorte.torneo.domain.ports.in.GolesJugadorUseCase;
import com.futnorte.torneo.domain.ports.in.JugadorUseCase;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.ActualizarEnfrentamientoRequest;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.CrearEnfrentamientoRequest;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.EnfrentamientoResponse;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enfrentamientos")
@CrossOrigin(origins = "*")
public class EnfrentamientoController {
    
    private final EnfrentamientoUseCase enfrentamientoUseCase;
    private final EquipoUseCase equipoUseCase;
    private final JugadorUseCase jugadorUseCase;
    private final GolesJugadorUseCase golesJugadorUseCase;

    private final ActualizarEnfrentamientoHandler actualizarEnfrentamientoHandler;



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

        ActualizacionEnfrentamientoCommand command = new ActualizacionEnfrentamientoCommand(
                id,
                request.getFechaHora(),
                request.getCancha(),
                request.getEstado(),
                request.getGolesLocal(),
                request.getGolesVisitante(),
                request.getGolesJugadoresLocal(),
                request.getGolesJugadoresVisitante()
        );
        Enfrentamiento enfrentamientoActualizado = enfrentamientoUseCase.actualizarEnfrentamiento(command);
        EnfrentamientoResponse response = toResponse(enfrentamientoActualizado);
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

        // Obtener goles de jugadores si el enfrentamiento está finalizado
        List<GolesJugadorResponse> golesJugadoresLocal = new ArrayList<>();
        List<GolesJugadorResponse> golesJugadoresVisitante = new ArrayList<>();

        if (enfrentamiento.estaFinalizado()) {
            List<GolesJugador> golesJugadores = golesJugadorUseCase.obtenerGolesJugadoresPorEnfrentamiento(enfrentamiento.getId());

            for (GolesJugador goles : golesJugadores) {
                var jugador = jugadorUseCase.buscarJugadorPorId(goles.getJugadorId());

                GolesJugadorResponse golesResponse = new GolesJugadorResponse(
                        goles.getJugadorId(),
                        jugador.getNombre(),
                        jugador.getApellido(),
                        goles.getCantidadGoles()
                );

                // Determinar si es jugador local o visitante
                if (jugador.getEquipoId().equals(enfrentamiento.getEquipoLocalId())) {
                    golesJugadoresLocal.add(golesResponse);
                } else {
                    golesJugadoresVisitante.add(golesResponse);
                }
            }
        }

        return new EnfrentamientoResponse(
                enfrentamiento.getId(),
                enfrentamiento.getTorneoId(),
                equipoLocalNombre,
                equipoVisitanteNombre,
                enfrentamiento.getFechaHora(),
                enfrentamiento.getCancha(),
                enfrentamiento.getEstado(),
                enfrentamiento.getGolesLocal(),
                enfrentamiento.getGolesVisitante(),
                golesJugadoresLocal,
                golesJugadoresVisitante
        );
    }
}