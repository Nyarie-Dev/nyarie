package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.domain.constant.map.TerrainType;

import java.util.ArrayList;
import java.util.List;

/// Holds the lists containing the game's assets mapped to their respective
/// [domain classes][eu.nyarie.core.domain.constant.Asset].
public class GameAssets {

    private final List<Region> regions;
    private final List<TerrainType> terrainTypes;

    GameAssets(List<Region> regions, List<TerrainType> terrainTypes) {
        this.regions = new ArrayList<>(regions);
        this.terrainTypes = new ArrayList<>(terrainTypes);
    }
}
