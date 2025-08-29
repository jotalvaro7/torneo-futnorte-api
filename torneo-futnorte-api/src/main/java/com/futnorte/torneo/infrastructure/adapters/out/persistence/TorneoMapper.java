package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.Torneo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TorneoMapper {
    
    @Mapping(target = "equiposIds", source = "equiposIds", qualifiedByName = "listToString")
    TorneoEntity toEntity(Torneo torneo);
    
    @Mapping(target = "equiposIds", source = "equiposIds", qualifiedByName = "stringToList")
    Torneo toDomain(TorneoEntity entity);
    
    @Named("listToString")
    default String listToString(List<Long> equiposIds) {
        if (equiposIds == null || equiposIds.isEmpty()) {
            return null;
        }
        return equiposIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
    
    @Named("stringToList")
    default List<Long> stringToList(String equiposIds) {
        if (equiposIds == null || equiposIds.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(equiposIds.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}