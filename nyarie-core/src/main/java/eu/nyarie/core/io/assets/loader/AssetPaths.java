package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.domain.constant.map.TerrainType;
import lombok.Getter;

import java.nio.file.Path;
import java.util.Set;

@Getter
class AssetPaths {
    static final Path ROOT = Path.of("assets");
    static final AssetFilePath<Region> REGIONS = new AssetFilePath<>(ROOT.resolve("map", "regions.json"), Region.class);
    static final AssetFilePath<TerrainType> TERRAIN_TYPES = new AssetFilePath<>(ROOT.resolve("map", "terrain-types.json"), TerrainType.class);

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
