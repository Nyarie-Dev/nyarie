package eu.nyarie.core.engine;

import eu.nyarie.core.DefaultServiceProfile;
import eu.nyarie.core.api.engine.EnginePersistenceContext;
import eu.nyarie.core.api.engine.EngineServiceProfile;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.service.FactionService;

public class CoreNyarieEngine extends NyarieEngine {


    public CoreNyarieEngine(EnginePersistenceContext persistenceContext) {
        super(persistenceContext);
    }

    public static CoreNyarieEnginePersistenceContextBuilder withPersistenceContext(EnginePersistenceContext context) {
        return new CoreNyarieEnginePersistenceContextBuilder(context);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public static class CoreNyarieEnginePersistenceContextBuilder {

        private final CoreNyarieEngineBuilder engineBuilder;

        public CoreNyarieEnginePersistenceContextBuilder(EnginePersistenceContext persistenceContext) {
            this.engineBuilder = new CoreNyarieEngineBuilder(persistenceContext);
        }

        public CoreNyarieEngineBuilder builder() {
            return engineBuilder;
        }

        public CoreNyarieEngineBuilder withDefaults() {
            return engineBuilder.loadPreset(new DefaultServiceProfile());
        }

    }

    public static class CoreNyarieEngineBuilder {

        private final CoreNyarieEngine engine;

        CoreNyarieEngineBuilder(EnginePersistenceContext persistenceContext) {
            this.engine = new CoreNyarieEngine(persistenceContext);
        }

        public CoreNyarieEngineBuilder loadPreset(EngineServiceProfile preset) {
            preset.getFactionService(engine)
                    .ifPresent(factionService -> engine.factionService = factionService);
            preset.getCharacterService(engine)
                    .ifPresent(characterService -> engine.characterService = characterService);

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
