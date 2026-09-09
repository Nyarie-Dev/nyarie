package eu.nyarie.core.api.persistence;

import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.data.faction.FactionData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FactionRepository {

    List<? extends FactionData> findAll();

    Optional<? extends FactionData> findById(UUID factionId);

    Optional<? extends FactionCharacterData> findByIdWithCharacters(UUID factionId);
}
