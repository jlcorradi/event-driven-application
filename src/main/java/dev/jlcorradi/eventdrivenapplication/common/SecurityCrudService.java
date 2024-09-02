package dev.jlcorradi.eventdrivenapplication.common;

import dev.jlcorradi.eventdrivenapplication.common.dataobject.SecurityDto;
import dev.jlcorradi.eventdrivenapplication.core.model.Security;

public interface SecurityCrudService extends CrudService<SecurityDto, String> {
    Security findBySymbol(String symbol);
}
