package eu.nyarie.core.api.data.faction;

import java.util.UUID;

public interface CreateFactionData {

    String getName();
    String getDescription();
    String getMotto();

    UUID getOriginFactionId();

    UUID getLeaderCharacterId();
}
