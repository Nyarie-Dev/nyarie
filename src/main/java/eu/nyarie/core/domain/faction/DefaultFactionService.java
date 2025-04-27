package eu.nyarie.core.domain.faction;

import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.faction.FactionData;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.service.FactionService;
import lombok.Getter;

@Getter
public class DefaultFactionService implements FactionService {

    private final NyarieEngine engine;

    public DefaultFactionService(NyarieEngine engine) {
        this.engine = engine;
    }

    @Override
    public FactionCommands createFromData(FactionData factionData) {
        return new Faction(factionData);
    }
}
