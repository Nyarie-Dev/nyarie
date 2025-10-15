package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;
import lombok.Getter;

import java.nio.file.Path;
import java.util.Set;

@Getter
class AssetPaths {
    static final Path ROOT = Path.of("assets");
    static final AssetFilePath<RegionAsset> REGIONS = new AssetFilePath<>(ROOT.resolve("map", "regions.json"), RegionAsset.class);
    static final AssetFilePath<TerrainTypeAsset> TERRAIN_TYPES = new AssetFilePath<>(ROOT.resolve("map", "terrain-types.json"), TerrainTypeAsset.class);

    /// Gets all the subpaths of the asset path as an [unmodifiable Set][java.util.Collections#unmodifiableSet(Set)].
    ///
    /// The subpaths are all the statically defined Paths of the [AssetPaths] class except for [ROOT][#ROOT].
    /// @return An unmodifiable [Set] containing all the subpaths of the asset path
    public static Set<AssetFilePath<?>> getSubpaths() {
        return Set.of(
                REGIONS,
                TERRAIN_TYPES
        );
    }

}
