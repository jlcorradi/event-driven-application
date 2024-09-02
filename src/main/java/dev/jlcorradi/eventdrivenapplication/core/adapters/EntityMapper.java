package dev.jlcorradi.eventdrivenapplication.core.adapters;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EntityMapper<D, T> {
    @Mapping(target = "id", ignore = true)
    T dto2Entity(@MappingTarget T target, D dto);

    D entity2Dto(T entity);
}