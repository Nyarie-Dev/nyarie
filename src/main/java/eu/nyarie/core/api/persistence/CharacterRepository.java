package eu.nyarie.core.api.persistence;

import eu.nyarie.core.api.data.character.CharacterData;

import java.util.Set;
import java.util.UUID;

public interface CharacterRepository {

    Set<CharacterData> getCharactersInFaction(UUID factionId);
    Set<CharacterData> getCharactersByName(String name);
}
