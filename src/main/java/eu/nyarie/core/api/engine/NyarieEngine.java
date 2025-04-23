package eu.nyarie.core.api.engine;

import eu.nyarie.core.api.service.FactionService;

public class NyarieEngine implements EngineServices {

    private FactionService factionService;

    private NyarieEngine() {}

    public static NyarieEngineBuilder builder() {
        return new NyarieEngineBuilder();
    }

    public static NyarieEngineBuilder builder(EngineServices preset) {
        return new NyarieEngineBuilder(preset);
    }

    private void start() {

    }

    @Override
    public FactionService factionService() {
        return factionService;
    }

    public static class NyarieEngineBuilder {

        private final NyarieEngine engine;

        private NyarieEngineBuilder() {
            this.engine = new NyarieEngine();
        }

        private NyarieEngineBuilder(EngineServices preset) {
            this();

            engine.factionService = preset.factionService();
        }

        public NyarieEngineBuilder factionService(FactionService factionService) {
            this.engine.factionService = factionService;
            return this;
        }

        public NyarieEngine start() {
            this.engine.start();
            return this.engine;
        }
    }
}
