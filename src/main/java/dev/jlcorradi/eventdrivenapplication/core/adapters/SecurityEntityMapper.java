package dev.jlcorradi.eventdrivenapplication.core.adapters;

import dev.jlcorradi.eventdrivenapplication.common.dataobject.SecurityDto;
import dev.jlcorradi.eventdrivenapplication.core.model.Security;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SecurityEntityMapper extends EntityMapper<SecurityDto, Security> {
    @Override
    @Mapping(target = "histories", ignore = true)
    Security dto2Entity(@MappingTarget Security target, SecurityDto dto);

    @Override
    SecurityDto entity2Dto(Security entity);
}
