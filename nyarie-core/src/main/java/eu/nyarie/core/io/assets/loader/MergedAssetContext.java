package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;

/// Record that represents the final, merged assets for the engine.
///
/// Result of merging all the assets of an [AssetContext].
record MergedAssetContext(
        RegionAsset regions,
        TerrainTypeAsset terrainTypes
) {

}