package eu.nyarie.core.api.data.faction;

import eu.nyarie.core.api.data.Identifiable;

import java.util.UUID;

public interface FactionData extends Identifiable {

    String getName();
    String getDescription();
    String getMotto();

    UUID getOriginFactionId();

    UUID getLeaderCharacterId();
}
