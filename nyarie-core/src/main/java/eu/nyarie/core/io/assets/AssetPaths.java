package eu.nyarie.core.io.assets;

import lombok.Getter;

import java.nio.file.Path;
import java.util.Set;

@Getter
class AssetPaths {
    static final Path ROOT = Path.of("assets");
    static final Path REGIONS = ROOT.resolve("map", "regions.json");

    /// Gets all the subpaths of the asset path as an [unmodifiable Set][java.util.Collections#unmodifiableSet(Set)].
    ///
    /// The subpaths are all the statically defined Paths of the [AssetPaths] class except for [ROOT][#ROOT].
    /// @return An unmodifiable [Set] containing all the subpaths of the asset path
    public static Set<Path> getSubpaths() {
        return Set.of(
                REGIONS
        );
    }

}
