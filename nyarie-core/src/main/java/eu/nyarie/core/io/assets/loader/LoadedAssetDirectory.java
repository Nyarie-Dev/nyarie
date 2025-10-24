package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;

import java.util.List;

/// Record that holds the loaded data of all the asset files.
record LoadedAssetDirectory(
        RegionAsset regions,
        TerrainTypeAsset terrainTypes
) {

    /// Counts how many loaded assets there are in this directory.
    /// @return An `int` count of loaded assets.
    public int count() {
        return allAssets().size();
    }

    /// Returns an unmodifiable list containing all the assets of this directory,
    /// including ones that were not loaded.
    /// @return An [unmodifiable List][List#of()] containing all assets, including ones that were not loaded.
    public List<AssetDto<?>> allAssets() {
        return List.of(
                regions,
                terrainTypes
        );
    }
}
