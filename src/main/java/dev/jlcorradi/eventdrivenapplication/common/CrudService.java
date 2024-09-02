package dev.jlcorradi.eventdrivenapplication.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CrudService<D, K> {
    D create(D dto);

    D update(K id, D dto);

    D get(K id);

    void delete(K id);

    Page<D> list(PageRequest pageRequest);

}