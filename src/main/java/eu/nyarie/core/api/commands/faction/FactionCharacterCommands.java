package eu.nyarie.core.api.commands.faction;

import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.commands.character.CharacterCommands;

public interface FactionCharacterCommands extends FactionCommands, FactionCharacterData {

    void setLeaderCharacter(CharacterCommands character);
}
