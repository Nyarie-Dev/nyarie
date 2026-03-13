package eu.nyarie.core.api.data.army;

import java.util.Set;
import java.util.UUID;

public interface CreateArmyData {

    String getName();
    UUID getFactionId();
    Set<UUID> getCombatUnitIds();
}
