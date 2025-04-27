package eu.nyarie.core.engine;

import eu.nyarie.core.CorePreset;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.service.CharacterService;
import eu.nyarie.core.api.service.FactionService;
import lombok.Getter;

@Getter
public class CoreNyarieEngine implements NyarieEngine {

    FactionService factionService;
    CharacterService characterService;

    public static CoreNyarieEngineBuilder builder() {
        return new CoreNyarieEngineBuilder();
    }

    public static CoreNyarieEngineBuilder withDefaults() {
        return new CoreNyarieEngineBuilder().loadPreset(new CorePreset());
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
