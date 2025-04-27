package eu.nyarie.core.api.service;

import eu.nyarie.core.api.commands.faction.FactionCharacterCommands;
import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.data.faction.FactionData;

public interface FactionService extends NyarieService {

    FactionCommands createFaction(FactionData factionData);
    FactionCharacterCommands createFactionCharacter(FactionCharacterData characterData);
}
