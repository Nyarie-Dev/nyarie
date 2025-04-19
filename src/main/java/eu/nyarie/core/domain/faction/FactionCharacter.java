package eu.nyarie.core.domain.faction;

import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.commands.character.CharacterCommands;
import eu.nyarie.core.api.commands.faction.FactionCharacterCommands;
import eu.nyarie.core.api.persistence.FactionRepository;
import eu.nyarie.core.domain.character.Character;
import lombok.val;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FactionCharacter extends Faction implements FactionCharacterCommands {

    private final Set<CharacterCommands> characters;

    FactionCharacter(FactionCharacterData factionCharacterData) {
        super(factionCharacterData);
        this.characters = factionCharacterData.getCharacters()
                .stream()
                .map(Character::new)
                .collect(Collectors.toSet());
    }

    public static FactionCharacter query(FactionRepository repository, UUID id) {
        val data = repository.getFactionWithCharacters(id);

        return new FactionCharacter(data.orElseThrow());
    }

    @Override
    public Set<CharacterData> getCharacters() {
        return Collections.unmodifiableSet(characters);
    }

    @Override
    public void addCharacter(CharacterCommands character) {
        characters.add(character);
    }

    @Override
    public void removeCharacter(CharacterCommands character) {
        characters.remove(character);
    }

    @Override
    public void setLeaderCharacter(CharacterCommands character) {
        this.leaderCharacterId = character.getId();
    }

}
