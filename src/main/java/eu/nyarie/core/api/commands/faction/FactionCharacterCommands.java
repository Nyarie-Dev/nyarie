package eu.nyarie.core.api.commands.faction;

import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.commands.character.CharacterCommands;

import java.util.Set;

public interface FactionCharacterCommands extends FactionCommands, FactionCharacterData {
    Set<CharacterData> getCharacters();

    void addCharacter(CharacterCommands character);
    void removeCharacter(CharacterCommands character);

    void setLeaderCharacter(CharacterCommands character);
}
