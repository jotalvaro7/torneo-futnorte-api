package com.futnorte.torneo.application.mapper;

import com.futnorte.torneo.application.dto.EnfrentamientoResponseDTO;
import com.futnorte.torneo.application.dto.GolesJugadorResponseDTO;
import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.ports.in.EquipoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EnfrentamientoApplicationMapper {

    private final EquipoUseCase equipoUseCase;

    public EnfrentamientoResponseDTO toEnfrentamientoResponseDTO(Enfrentamiento enfrentamiento, List<GolesJugadorResponseDTO> golesJugadoresLocal, List<GolesJugadorResponseDTO> golesJugadoresVisitante) {
        String equipoLocalNombre = equipoUseCase.buscarEquipoPorId(enfrentamiento.getEquipoLocalId()).getNombre();
        String equipoVisitanteNombre = equipoUseCase.buscarEquipoPorId(enfrentamiento.getEquipoVisitanteId()).getNombre();


        return new EnfrentamientoResponseDTO(
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
