package eu.nyarie.core.api.engine;

import eu.nyarie.core.api.service.FactionService;

import java.util.Optional;

public interface EnginePreset {

    Optional<FactionService> getFactionService(NyarieEngine engine);
}
