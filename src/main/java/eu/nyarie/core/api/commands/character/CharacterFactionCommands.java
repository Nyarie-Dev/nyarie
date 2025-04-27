package eu.nyarie.core.api.commands.character;

import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.character.CharacterFactionData;

public interface CharacterFactionCommands extends CharacterCommands, CharacterFactionData {

    void joinFaction(FactionCommands faction);
    void leaveFaction();
}
