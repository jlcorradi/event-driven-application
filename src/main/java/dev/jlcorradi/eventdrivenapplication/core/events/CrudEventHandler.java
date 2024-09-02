package dev.jlcorradi.eventdrivenapplication.core.events;

import dev.jlcorradi.eventdrivenapplication.core.model.BaseEntity;

import java.util.Arrays;
import java.util.Optional;

public interface CrudEventHandler<T extends BaseEntity> {
    void onPerformOperation(Object source, CrudOperationType operationType, T entity);

    default boolean supports(CrudOperationType operationType, T entity) {
        return Optional.ofNullable(getClass().getAnnotation(CrudEventListener.class))
                .map(crudEventListener -> Arrays.asList(crudEventListener.supportedOperationTypes()).contains(operationType))
                .orElse(true);
    }

    default int order() {
        return Optional.ofNullable(getClass().getAnnotation(CrudEventListener.class))
                .map(CrudEventListener::order)
                .orElse(Integer.MAX_VALUE);
    }
}
