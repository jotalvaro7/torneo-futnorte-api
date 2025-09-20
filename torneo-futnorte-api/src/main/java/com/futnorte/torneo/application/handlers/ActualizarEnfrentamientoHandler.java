package com.futnorte.torneo.application.handlers;

import com.futnorte.torneo.application.commands.ActualizacionEnfrentamientoCommand;
import com.futnorte.torneo.domain.entities.Enfrentamiento;
import com.futnorte.torneo.domain.entities.GolesJugador;
import com.futnorte.torneo.domain.exceptions.EntityNotFoundException;
import com.futnorte.torneo.domain.ports.in.EstadisticasEquipoUseCase;
import com.futnorte.torneo.domain.ports.in.GolesJugadorUseCase;
import com.futnorte.torneo.domain.ports.out.EnfrentamientoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ActualizarEnfrentamientoHandler implements  CommandHandler<ActualizacionEnfrentamientoCommand, Enfrentamiento>{

    private final EnfrentamientoRepositoryPort enfrentamientoRepositoryPort;
    private final EstadisticasEquipoUseCase estadisticasEquipoUseCase;
    private final GolesJugadorUseCase golesJugadorUseCase;

    @Override
    public Enfrentamiento handle(ActualizacionEnfrentamientoCommand command) {

        Enfrentamiento enfrentamiento = obtenerEnfrentamiento(command);
        List<GolesJugador> golesExistentes = obtenerGolesExistentes(command);

        actualizarEnfrentamiento(command, enfrentamiento);

        if(command.esPartidoFinalizado()) {
            actualizarEstadisticas(enfrentamiento);
        }

        Enfrentamiento enfrentamientoGuardado = enfrentamientoRepositoryPort.save(enfrentamiento);

        if(command.tieneGolesJugadores()) {
            procesarGolesJugadores(command, enfrentamientoGuardado, golesExistentes);
        }

        return enfrentamientoGuardado;
    }

    private void procesarGolesJugadores(ActualizacionEnfrentamientoCommand command, Enfrentamiento enfrentamientoGuardado, List<GolesJugador> golesExistentes) {
        golesJugadorUseCase.procesarGolesJugadores(
                enfrentamientoGuardado.getId(),
                enfrentamientoGuardado.getEquipoLocalId(),
                enfrentamientoGuardado.getEquipoVisitanteId(),
                command.golesJugadoresLocal(),
                command.golesJugadoresVisitante(),
                golesExistentes
        );
    }

    private void actualizarEstadisticas(Enfrentamiento enfrentamiento) {
        estadisticasEquipoUseCase.actualizarEstadisticas(
                enfrentamiento.getEquipoLocalId(),
                enfrentamiento.getEquipoVisitanteId(),
                enfrentamiento.getGolesLocal(),
                enfrentamiento.getGolesVisitante());
    }

    private void actualizarEnfrentamiento(ActualizacionEnfrentamientoCommand command, Enfrentamiento enfrentamiento) {
        enfrentamiento.actualizar(
                command.fechaHora(),
                command.cancha(),
                command.estadoEnfrentamiento(),
                command.golesLocal(),
                command.golesVisitante()
        );
    }

    private List<GolesJugador> obtenerGolesExistentes(ActualizacionEnfrentamientoCommand command) {
        return golesJugadorUseCase.obtenerGolesJugadoresPorEnfrentamiento(command.enfrentamientoId());
    }

    private Enfrentamiento obtenerEnfrentamiento(ActualizacionEnfrentamientoCommand command) {
        return enfrentamientoRepositoryPort.findById(command.enfrentamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Enfrentamiento", command.enfrentamientoId()));
    }


}
