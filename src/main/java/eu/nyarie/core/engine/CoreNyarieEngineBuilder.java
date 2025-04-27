package eu.nyarie.core.engine;

import eu.nyarie.core.api.engine.EnginePreset;
import eu.nyarie.core.api.service.FactionService;

public class CoreNyarieEngineBuilder {

        private final CoreNyarieEngine engine;

        CoreNyarieEngineBuilder() {
            this.engine = new CoreNyarieEngine();
        }

        public CoreNyarieEngineBuilder loadPreset(EnginePreset preset) {
            preset.getFactionService(engine)
                    .ifPresent(factionService -> engine.factionService = factionService);

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