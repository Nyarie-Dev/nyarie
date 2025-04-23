package eu.nyarie.core.domain.faction;

import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.faction.FactionData;
import eu.nyarie.core.api.service.FactionService;

public class FactionServiceProvider implements FactionService {

    @Override
    public FactionCommands createFromData(FactionData factionData) {
        return new Faction(factionData);
    }
}
