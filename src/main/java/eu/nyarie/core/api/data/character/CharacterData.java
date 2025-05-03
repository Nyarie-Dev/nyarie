package eu.nyarie.core.api.data.character;

import eu.nyarie.core.api.data.Identifiable;

import java.util.UUID;

public interface CharacterData extends Identifiable, CreateCharacterData {

    float getHealth();

    String getCurrentRegionId();
    UUID getCommandedGroupId();
}
