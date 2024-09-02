package dev.jlcorradi.eventdrivenapplication.core.adapters;

import dev.jlcorradi.eventdrivenapplication.common.SecurityCrudService;
import dev.jlcorradi.eventdrivenapplication.common.dataobject.OperationDto;
import dev.jlcorradi.eventdrivenapplication.core.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = SecurityCrudService.class)
public interface OperationEntityMapper extends EntityMapper<OperationDto, Operation> {
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "security", source = "securitySymbol")
    Operation dto2Entity(@MappingTarget Operation target, OperationDto dto);
}
