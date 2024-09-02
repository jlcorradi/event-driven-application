package dev.jlcorradi.eventdrivenapplication.core.events;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrudEventListener {
    CrudOperationType[] supportedOperationTypes()
            default {CrudOperationType.CREATE, CrudOperationType.DELETE, CrudOperationType.UPDATE};

    int order() default Integer.MAX_VALUE;
}
