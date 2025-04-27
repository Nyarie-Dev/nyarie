package eu.nyarie.core.domain.faction;

import eu.nyarie.core.api.commands.character.CharacterCommands;
import eu.nyarie.core.api.commands.faction.FactionCharacterCommands;
import eu.nyarie.core.api.data.faction.FactionData;

import java.util.*;

public class FactionCharacter extends Faction implements FactionCharacterCommands {

    private final Set<CharacterCommands> characters;

    FactionCharacter(FactionData factionData, Collection<CharacterCommands> characters) {
        super(factionData);
        this.characters = new HashSet<>(characters);
    }

    @Override
    public Set<CharacterCommands> getCharacters() {
        return Collections.unmodifiableSet(characters);
    }

    @Override
    public void setLeaderCharacter(CharacterCommands character) {
        this.leaderCharacterId = character.getId();
    }

}
