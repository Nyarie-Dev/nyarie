package eu.nyarie.core.io.appdata;

import java.nio.file.Path;
import java.util.Set;

/// Defines the paths of the app data directory ([ROOT][#ROOT]) and its subpaths
///
/// For more information on how the [ROOT][#ROOT] app data path is determined and how it can be configured,
/// see [AppDataPathResolver].
///
/// @see AppDataPathResolver
final class AppDataPaths {

    static final Path ROOT = new AppDataPathResolver().determineAppDataDirectoryPath();
    static final Path MODS = ROOT.resolve("mods");

    /// Gets all the subpaths of the app data path as an [unmodifiable Set][java.util.Collections#unmodifiableSet(Set)].
    ///
    /// The subpaths are all the statically defined Paths of the [AppDataPaths] class except for [ROOT][#ROOT].
    /// @return An unmodifiable [Set] containing all the subpaths of the app data path
    public static Set<Path> getSubpaths() {
        return Set.of(
                MODS
        );
    }

}
