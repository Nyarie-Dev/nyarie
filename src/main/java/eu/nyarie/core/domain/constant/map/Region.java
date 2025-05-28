package eu.nyarie.core.domain.constant.map;

import eu.nyarie.core.api.data.map.RegionData;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Region implements RegionData {

    private final String id;
    private final String name;
    private final TerrainType terrainType;

    private final Set<Region> neighboringRegions;

    private final Set<UUID> claimantFactionIds;
    private final Set<UUID> claimbuildIds;

    public Region(String id, String name, TerrainType terrainType, Set<Region> neighboringRegions, Set<UUID> claimantFactionIds, Set<UUID> claimbuildIds) {
        this.id = id;
        this.name = name;
        this.terrainType = terrainType;
        this.neighboringRegions = new HashSet<>(neighboringRegions);
        this.claimantFactionIds = new HashSet<>(claimantFactionIds);
        this.claimbuildIds = new HashSet<>(claimbuildIds);
    }

    public Set<RegionData> getNeighboringRegions() {
        return Collections.unmodifiableSet(neighboringRegions);
    }

    public Set<UUID> getClaimantFactionIds() {
        return Collections.unmodifiableSet(claimantFactionIds);
    }

    public Set<UUID> getClaimbuildIds() {
        return Collections.unmodifiableSet(claimbuildIds);
    }
}
