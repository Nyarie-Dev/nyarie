package eu.nyarie.core.api.data.faction;

import eu.nyarie.core.api.data.character.CharacterData;

import java.util.Set;

public interface FactionCharacterData extends FactionData {
    Set<? extends CharacterData> getCharacters();
}
