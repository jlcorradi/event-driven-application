package dev.jlcorradi.eventdrivenapplication.web.api;

import dev.jlcorradi.eventdrivenapplication.common.OperationCrudService;
import dev.jlcorradi.eventdrivenapplication.common.dataobject.OperationDto;
import dev.jlcorradi.eventdrivenapplication.web.EndPointConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(EndPointConstants.OPERATIONS_API)
public class OperationApi extends AbstractCrudApi<OperationDto, UUID, OperationCrudService> {

    public OperationApi(OperationCrudService service) {
        super(service);
    }
}
