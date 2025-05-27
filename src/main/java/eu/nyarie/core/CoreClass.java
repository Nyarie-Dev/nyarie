package eu.nyarie.core;

import eu.nyarie.core.api.ApiClass;
import eu.nyarie.core.api.engine.EnginePersistenceContext;
import eu.nyarie.core.engine.CoreNyarieEngine;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class CoreClass {

    String accessApi(EnginePersistenceContext persistenceContext) {
        log.error("Hello World!");
        val apiClass = new ApiClass();

        val engine = CoreNyarieEngine
                .withPersistenceContext(persistenceContext)
                .withDefaults()
                .build();

        engine.start();
        return apiClass.getMessage();
    }
}
