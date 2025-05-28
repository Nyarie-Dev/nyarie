package eu.nyarie.core.domain.constant;

import eu.nyarie.core.api.data.map.TerrainTypeData;

import java.time.Duration;

/**
 * @param getId   id = GRASS_FIELDS
 * @param getName name = Grassy Fields
 */
public record TerrainType(
        String getId,
        String getName,
        Duration getMovementDuration
) implements TerrainTypeData {

}
