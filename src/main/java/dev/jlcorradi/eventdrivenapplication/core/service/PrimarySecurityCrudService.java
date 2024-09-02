package dev.jlcorradi.eventdrivenapplication.core.service;

import dev.jlcorradi.eventdrivenapplication.common.SecurityCrudService;
import dev.jlcorradi.eventdrivenapplication.common.dataobject.SecurityDto;
import dev.jlcorradi.eventdrivenapplication.core.adapters.SecurityEntityMapper;
import dev.jlcorradi.eventdrivenapplication.core.events.SecurityCrudEventHandler;
import dev.jlcorradi.eventdrivenapplication.core.model.Security;
import dev.jlcorradi.eventdrivenapplication.core.repository.SecurityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimarySecurityCrudService
        extends AbstractCrudService<SecurityDto, Security, String, SecurityRepository, SecurityEntityMapper>
        implements SecurityCrudService {

    private final SecurityRepository securityRepository;

    public PrimarySecurityCrudService(SecurityRepository repository, SecurityEntityMapper mapper,
                                      List<SecurityCrudEventHandler> listeners, SecurityRepository securityRepository) {
        super(repository, mapper, listeners);
        this.securityRepository = securityRepository;
    }


    @Override
    public Security findBySymbol(String symbol) {
        return securityRepository.findById(symbol).orElse(null);
    }
}
