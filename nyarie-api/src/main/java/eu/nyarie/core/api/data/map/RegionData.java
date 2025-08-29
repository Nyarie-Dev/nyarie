package eu.nyarie.core.api.data.map;

import java.util.Set;
import java.util.UUID;

public interface RegionData {

    String getId();
    String getName();
    TerrainTypeData getTerrainType();

    Set<RegionData> getNeighboringRegions();

    Set<UUID> getClaimantFactionIds();
    Set<UUID> getClaimbuildIds();

}
