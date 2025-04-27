package eu.nyarie.core.domain.character;

import eu.nyarie.core.api.commands.character.CharacterFactionCommands;
import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.character.CharacterData;
import lombok.Getter;

@Getter
public class CharacterFaction extends Character implements CharacterFactionCommands {

    private FactionCommands faction;

    public CharacterFaction(CharacterData characterData, FactionCommands factionCommands) {
        super(characterData);
    }

    @Override
    public void joinFaction(FactionCommands faction) {
        this.faction = faction;
    }

    @Override
    public void leaveFaction() {
        this.faction = null;
    }
}
