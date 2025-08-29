package eu.nyarie.core.api.data.map;

import java.time.Duration;

public interface TerrainTypeData {

    String getId();
    String getName();
    Duration getMovementDuration();
}
