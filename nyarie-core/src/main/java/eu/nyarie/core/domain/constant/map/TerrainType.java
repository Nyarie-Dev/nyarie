package eu.nyarie.core.domain.constant.map;

import eu.nyarie.core.api.data.map.TerrainTypeData;
import eu.nyarie.core.domain.constant.Asset;

import java.time.Duration;

/**
 * Represents the type of terrain a region can have.
 * @param getId   id = GRASS_FIELDS
 * @param getName name = Grassy Fields
 */
public record TerrainType(
        String getId,
        String getName,
        Duration getMovementDuration
) implements TerrainTypeData, Asset {

}
