package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Torneo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TorneoMapper {
    
    TorneoEntity toEntity(Torneo torneo);
    
    Torneo toDomain(TorneoEntity entity);
}