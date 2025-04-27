package eu.nyarie.core;

import eu.nyarie.core.api.engine.EnginePreset;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.service.FactionService;
import eu.nyarie.core.domain.faction.DefaultFactionService;

import java.util.Optional;

public class CorePreset implements EnginePreset {

    @Override
    public Optional<FactionService> getFactionService(NyarieEngine engine) {
        return Optional.of(new DefaultFactionService(engine));
    }

}
