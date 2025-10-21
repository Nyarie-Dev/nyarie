package eu.nyarie.core.io.assets;

import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;

/// Record that holds the loaded data of all the asset files.
public record LoadedAssetDirectory(
        RegionAsset regions,
        TerrainTypeAsset terrainTypes
) {
}
