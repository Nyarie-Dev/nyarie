package eu.nyarie.core.io.init;

import lombok.Getter;

import java.nio.file.Path;
import java.util.Set;

@Getter
class InitFilePaths {
    static final Path ROOT = Path.of("init");
    static final InitFilePath<TerrainTypeCsvContent> TERRAIN_TYPES = new InitFilePath<>(ROOT.resolve("map", "terrain-types.csv"), TerrainTypeCsvContent.class);

    /// Gets all the subpaths of the init file path as an [unmodifiable Set][java.util.Collections#unmodifiableSet(Set)].
    ///
    /// The subpaths are all the statically defined Paths of the [InitFilePaths] class except for [ROOT][#ROOT].
    /// @return An unmodifiable [Set] containing all the subpaths of the asset path
    public static Set<InitFilePath<?>> getSubpaths() {
        return Set.of(
                TERRAIN_TYPES
        );
    }

}
