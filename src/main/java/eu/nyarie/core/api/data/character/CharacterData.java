package eu.nyarie.core.api.data.character;

import eu.nyarie.core.api.data.Identifiable;

import java.util.UUID;

public interface CharacterData extends Identifiable {

    String getName();
    String getTitle();
    float getHealth();
    String getGear();

    UUID getFactionId();
    String getCurrentRegionId();
    UUID getCommandedGroupId();
}
