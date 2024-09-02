package dev.jlcorradi.eventdrivenapplication.web.api;

import dev.jlcorradi.eventdrivenapplication.common.SecurityCrudService;
import dev.jlcorradi.eventdrivenapplication.common.dataobject.SecurityDto;
import dev.jlcorradi.eventdrivenapplication.web.EndPointConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPointConstants.SECURITIES_API)
public class SecurityCrudApi extends AbstractCrudApi<SecurityDto, String, SecurityCrudService> {
    public SecurityCrudApi(SecurityCrudService service) {
        super(service);
    }
}
