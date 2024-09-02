package dev.jlcorradi.eventdrivenapplication.core.service;

import dev.jlcorradi.eventdrivenapplication.common.CrudService;
import dev.jlcorradi.eventdrivenapplication.common.exceptions.EntityNotFoundException;
import dev.jlcorradi.eventdrivenapplication.core.adapters.EntityMapper;
import dev.jlcorradi.eventdrivenapplication.core.events.CrudEventHandler;
import dev.jlcorradi.eventdrivenapplication.core.events.CrudOperationType;
import dev.jlcorradi.eventdrivenapplication.core.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCrudService<D, T extends BaseEntity,
        K,
        R extends JpaRepository<T, K>,
        M extends EntityMapper<D, T>>
        implements CrudService<D, K> {

    public static final int ENTITY_GENERIC_PARAM_IDX = 1;

    private final R repository;
    private final M mapper;

    private final List<? extends CrudEventHandler<T>> eventListeners;

    @Override
    @Transactional
    public D create(D dto) {
        log.debug("Creating object: \n{}", dto);
        T entity = mapper.dto2Entity(getInstanceOfTypeParameter(super.getClass(), ENTITY_GENERIC_PARAM_IDX), dto);
        performValidations(entity);
        T newlySaved = repository.save(entity);
        publishEvent(CrudOperationType.CREATE, entity);
        return mapper.entity2Dto(newlySaved);
    }

    @Transactional
    @Override
    public D update(K id, D dto) {
        log.debug("Updating object: \n{}", dto);
        T entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        performValidations(entity);
        T newlySaved = mapper.dto2Entity(entity, dto);
        T updated = repository.save(newlySaved);
        publishEvent(CrudOperationType.UPDATE, entity);
        return mapper.entity2Dto(updated);
    }

    @Override
    public D get(K id) {
        return repository.findById(id)
                .map(mapper::entity2Dto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public void delete(K id) {
        repository.findById(id).ifPresentOrElse(entity -> {
            repository.delete(entity);
            publishEvent(CrudOperationType.DELETE, entity);
        }, () -> {
            log.warn("Tried to delete non existing entity. Id: {}", id);
        });
    }

    @Override
    public Page<D> list(PageRequest pageRequest) {
        Page<T> all = repository.findAll(pageRequest);
        List<D> content = all.getContent().stream().map(mapper::entity2Dto).toList();
        return new PageImpl<>(content, pageRequest, all.getTotalElements());
    }

    /***
     * Perform validations before saving.
     * <p>
     * Override this method for best abstraction
     */
    public void performValidations(T entity) {
    }

    private void publishEvent(CrudOperationType operationType, T payload) {
        log.info("Publishing Event {}. Entity: {}", operationType, payload);
        this.eventListeners.stream()
                .filter(tCrudEvent -> tCrudEvent.supports(operationType, payload))
                .sorted(Comparator.comparingInt(CrudEventHandler::order))
                .forEach(tCrudEvent -> {
                    tCrudEvent.onPerformOperation(operationType, operationType, payload);
                });

    }

    @SuppressWarnings("unchecked")
    private T getInstanceOfTypeParameter(Class<?> contextClass, int paramIndex) {
        ParameterizedType superClass = (ParameterizedType) contextClass.getGenericSuperclass();
        try {
            Class<T> actualTypeArgument = (Class<T>) superClass.getActualTypeArguments()[paramIndex];
            Constructor<T> constructor = actualTypeArgument.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            // Oops, no default constructor
            throw new IllegalArgumentException(
                    String.format("Default constructor not found for class %s", superClass.getTypeName()), e);
        }
    }
}
