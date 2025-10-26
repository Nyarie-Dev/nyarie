package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.map.RegionsAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/// Record that holds the loaded data of all the asset files.
record LoadedAssetDirectory(
        Optional<RegionsAsset> regions,
        Optional<TerrainTypeAsset> terrainTypes
) {

    /// Counts how many loaded assets there are in this directory.
    /// @return An `int` count of loaded assets.
    public int count() {
        return allAssets().size();
    }

    /// Returns an unmodifiable list containing all the
    /// loaded asset files of this directory.
    /// @return An [unmodifiable List][Collections#unmodifiableList(List)] containing all loaded asset files.
    public List<AssetDto<?>> allAssets() {
        val list = new ArrayList<AssetDto<?>>(2);
        regions.ifPresent(list::add);
        terrainTypes.ifPresent(list::add);
        return Collections.unmodifiableList(list);
    }
}
