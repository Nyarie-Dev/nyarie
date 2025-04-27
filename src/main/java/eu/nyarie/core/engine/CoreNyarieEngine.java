package eu.nyarie.core.engine;

import eu.nyarie.core.CorePreset;
import eu.nyarie.core.api.engine.EnginePreset;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.service.FactionService;
import lombok.Getter;

@Getter
public class CoreNyarieEngine implements NyarieEngine, EnginePreset {

    private FactionService factionService;

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

    public static class CoreNyarieEngineBuilder {

        private final CoreNyarieEngine engine;

        private CoreNyarieEngineBuilder() {
            this.engine = new CoreNyarieEngine();
        }

        public CoreNyarieEngineBuilder loadPreset(EnginePreset preset) {
            if(preset.getFactionService() != null)
                engine.factionService = preset.getFactionService();

            return this;
        }

        public CoreNyarieEngineBuilder factionService(FactionService factionService) {
            this.engine.factionService = factionService;
            return this;
        }

        public CoreNyarieEngine build() {
            return this.engine;
        }
    }
}
