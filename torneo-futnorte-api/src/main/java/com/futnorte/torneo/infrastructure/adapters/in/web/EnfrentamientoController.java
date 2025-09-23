package com.futnorte.torneo.infrastructure.adapters.in.web;

import com.futnorte.torneo.application.dto.ActualizarEnfrentamientoApplicationDTO;
import com.futnorte.torneo.application.dto.EnfrentamientoResponseDTO;
import com.futnorte.torneo.domain.ports.in.EnfrentamientoUseCase;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.ActualizarEnfrentamientoRequestDTO;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.CrearEnfrentamientoRequest;
import com.futnorte.torneo.infrastructure.mappers.EnfrentamientoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enfrentamientos")
@CrossOrigin(origins = "*")
public class EnfrentamientoController {

    private final EnfrentamientoUseCase enfrentamientoUseCase;
    private final EnfrentamientoMapper enfrentamientoMapper;

    @PostMapping
    public ResponseEntity<EnfrentamientoResponseDTO> crearEnfrentamiento(@Valid @RequestBody CrearEnfrentamientoRequest request) {
        EnfrentamientoResponseDTO enfrentamientoResponseDTO = enfrentamientoUseCase.crearEnfrentamiento(
                request.getTorneoId(),
                request.getEquipoLocalId(),
                request.getEquipoVisitanteId(),
                request.getFechaHora(),
                request.getCancha()
        );

        return new ResponseEntity<>(enfrentamientoResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnfrentamientoResponseDTO> obtenerEnfrentamiento(@PathVariable Long id) {
        return enfrentamientoUseCase.obtenerEnfrentamientoPorId(id)
                .map(enfrentamientoResponseDTO ->
                        new ResponseEntity<>(enfrentamientoResponseDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<EnfrentamientoResponseDTO>> obtenerEnfrentamientosPorTorneo(@PathVariable Long torneoId) {
        List<EnfrentamientoResponseDTO> enfrentamientoResponseDTOS = enfrentamientoUseCase.obtenerEnfrentamientosPorTorneo(torneoId);
        return new ResponseEntity<>(enfrentamientoResponseDTOS, HttpStatus.OK);

    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<EnfrentamientoResponseDTO>> obtenerEnfrentamientosPorEquipo(@PathVariable Long equipoId) {
        List<EnfrentamientoResponseDTO> enfrentamientoResponseDTOS = enfrentamientoUseCase.obtenerEnfrentamientosPorEquipo(equipoId);
        return new ResponseEntity<>(enfrentamientoResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<EnfrentamientoResponseDTO>> obtenerEnfrentamientosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<EnfrentamientoResponseDTO> enfrentamientoResponseDTOS = enfrentamientoUseCase.obtenerEnfrentamientosPorFecha(fechaInicio, fechaFin);
        return new ResponseEntity<>(enfrentamientoResponseDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnfrentamientoResponseDTO> actualizarEnfrentamiento(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEnfrentamientoRequestDTO request) {

        ActualizarEnfrentamientoApplicationDTO actualizarEnfrentamientoApplicationDTO = enfrentamientoMapper.toApplication(request, id);
        EnfrentamientoResponseDTO enfrentamientoResponseDTO = enfrentamientoUseCase.actualizarEnfrentamiento(actualizarEnfrentamientoApplicationDTO);
        return new ResponseEntity<>(enfrentamientoResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnfrentamiento(@PathVariable Long id) {
        enfrentamientoUseCase.eliminarEnfrentamiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
