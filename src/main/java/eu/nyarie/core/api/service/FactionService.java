package eu.nyarie.core.api.service;

import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.faction.FactionData;

public interface FactionService extends NyarieService {

    FactionCommands createFromData(FactionData factionData);

}
