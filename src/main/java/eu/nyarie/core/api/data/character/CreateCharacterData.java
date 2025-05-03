package eu.nyarie.core.api.data.character;

import java.util.UUID;

public interface CreateCharacterData {
    String getName();
    String getTitle();
    String getGear();
    UUID getFactionId();
}
