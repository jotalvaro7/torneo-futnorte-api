package com.futnorte.torneo.application.dto;

import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EnfrentamientoResponseDTO {
       private Long id;
       private Long torneoId;
       private String equipoLocal;
       private String equipoVisitante;
       private LocalDateTime fechaHora;
       private String cancha;
       private EstadoEnfrentamiento estado;
       private Integer golesLocal;
       private Integer golesVisitante;
       private List<GolesJugadorResponseDTO> golesJugadoresLocal;
       private List<GolesJugadorResponseDTO> golesJugadoresVisitante;
}
