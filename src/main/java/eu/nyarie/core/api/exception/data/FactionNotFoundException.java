package eu.nyarie.core.api.exception.data;

import eu.nyarie.core.api.data.faction.FactionData;

import java.util.UUID;

/// Exception for when {@link FactionData} could not be found.
public class FactionNotFoundException extends NotFoundException {

    private static final String MSG_FACTION_WITH_ID_NOT_FOUND = "Faction with id '%s' not found!";

    public FactionNotFoundException(String message) {
        super(message);
    }

    public FactionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /// @param factionId The ID for which no {@link FactionData} could be found.
    /// @return A {@link FactionNotFoundException} with the `message`: {@value MSG_FACTION_WITH_ID_NOT_FOUND}
    public static FactionNotFoundException factionWithIdNotFound(UUID factionId) {
        return new FactionNotFoundException(MSG_FACTION_WITH_ID_NOT_FOUND.formatted(factionId));
    }
}
