package eu.nyarie.core;

import eu.nyarie.core.api.engine.EngineServices;
import eu.nyarie.core.api.service.FactionService;
import eu.nyarie.core.domain.faction.FactionServiceProvider;

public class CoreServices implements EngineServices {

    @Override
    public FactionService factionService() {
        return new FactionServiceProvider();
    }
}
