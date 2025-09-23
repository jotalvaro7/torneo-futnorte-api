package com.futnorte.torneo.infrastructure.mappers;

import com.futnorte.torneo.application.dto.ActualizarEnfrentamientoApplicationDTO;
import com.futnorte.torneo.application.dto.GolesJugadorApplicationDTO;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.ActualizarEnfrentamientoRequestDTO;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.GolesJugadorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface EnfrentamientoMapper {

    @Mapping(target = "enfrentamientoId", source = "id")
    @Mapping(target = "estadoEnfrentamiento", source = "request.estado")
    ActualizarEnfrentamientoApplicationDTO toApplication(ActualizarEnfrentamientoRequestDTO request, Long id);

    GolesJugadorApplicationDTO toApplication(GolesJugadorDto dto);

    List<GolesJugadorApplicationDTO> toApplication(List<GolesJugadorDto> goles);

}
