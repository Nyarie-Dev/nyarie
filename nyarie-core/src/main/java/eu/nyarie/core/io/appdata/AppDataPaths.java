package eu.nyarie.core.io.appdata;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Set;

/// Defines the paths of the app data directory ([ROOT][#ROOT]) and its subpaths.
///
/// For more information on how the [ROOT][#ROOT] app data path is determined and how it can be configured,
/// see [AppDataPathResolver].
///
/// @see AppDataPathResolver
@Slf4j
final class AppDataPaths {

    final Path ROOT;
    final Path MODS;

    public AppDataPaths() {
        log.debug("Initializing app data directory paths");
        this.ROOT = new AppDataPathResolver().determineAppDataDirectoryPath();
        this.MODS = ROOT.resolve("mods");
    }

    /// Gets all the subpaths of the app data path as an [unmodifiable Set][java.util.Collections#unmodifiableSet(Set)].
    ///
    /// The subpaths are all the statically defined Paths of the [AppDataPaths] class except for [ROOT][#ROOT].
    /// @return An unmodifiable [Set] containing all the subpaths of the app data path
    public Set<Path> getSubpaths() {
        return Set.of(
                MODS
        );
    }

}
