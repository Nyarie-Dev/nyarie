package eu.nyarie.core;

import eu.nyarie.core.api.engine.EnginePreset;
import eu.nyarie.core.api.service.FactionService;
import eu.nyarie.core.domain.faction.DefaultFactionService;

public class CorePreset implements EnginePreset {

    @Override
    public FactionService getFactionService() {
        return new DefaultFactionService();
    }
}
