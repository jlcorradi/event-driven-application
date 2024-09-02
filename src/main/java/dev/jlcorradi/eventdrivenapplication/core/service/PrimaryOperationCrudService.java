package dev.jlcorradi.eventdrivenapplication.core.service;

import dev.jlcorradi.eventdrivenapplication.common.OperationCrudService;
import dev.jlcorradi.eventdrivenapplication.common.dataobject.OperationDto;
import dev.jlcorradi.eventdrivenapplication.core.adapters.OperationEntityMapper;
import dev.jlcorradi.eventdrivenapplication.core.events.OperationCrudEventHandler;
import dev.jlcorradi.eventdrivenapplication.core.model.Operation;
import dev.jlcorradi.eventdrivenapplication.core.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PrimaryOperationCrudService
        extends AbstractCrudService<OperationDto, Operation, UUID, OperationRepository, OperationEntityMapper>
        implements OperationCrudService {

    public PrimaryOperationCrudService(OperationRepository repository, OperationEntityMapper mapper,
                                       List<OperationCrudEventHandler> listeners) {
        super(repository, mapper, listeners);
    }
}
