package dev.jlcorradi.eventdrivenapplication.core.service;

import dev.jlcorradi.eventdrivenapplication.core.events.CrudEventListener;
import dev.jlcorradi.eventdrivenapplication.core.events.CrudOperationType;
import dev.jlcorradi.eventdrivenapplication.core.events.OperationCrudEventHandler;
import dev.jlcorradi.eventdrivenapplication.core.model.Operation;
import dev.jlcorradi.eventdrivenapplication.core.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@CrudEventListener(supportedOperationTypes = {CrudOperationType.CREATE}, order = 0)
public class PortfolioManager implements OperationCrudEventHandler {
    private final PortfolioRepository portfolioRepository;

    @Async
    @Override
    public void onPerformOperation(Object source, CrudOperationType operationType, Operation entity) {
        log.info("Will maintain portfolio");
        try {
            Thread.sleep(5000);
            log.info("Done Mantaining Portfolio");
        } catch (InterruptedException ignored) {

        }
    }
}
