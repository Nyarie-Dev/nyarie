package eu.nyarie.core.io.installation;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Set;

/// Defines the paths of the installation directory ([ROOT][#ROOT]) and its subdirectories
///
/// For more information on how the [ROOT][#ROOT] installation path is determined and how it can be configured,
/// see [InstallationPathResolver].
///
/// @see InstallationPathResolver
@Slf4j
class InstallationPaths {
    static final Path ROOT = new InstallationPathResolver().determineInstallationDirectoryPath();
    static final Path ASSETS = ROOT.resolve("assets");
    
    /// Gets all the subdirectories of the installation path as an [unmodifiable Set][java.util.Collections#unmodifiableSet(Set)].
    /// 
    /// The subdirectories are all the statically defined Paths of the [InstallationPaths] class with the exception of [ROOT][#ROOT].
    /// @return An unmodifiable [Set] containing all the subdirectories of the installation path
    public static Set<Path> getSubdirectories() {
        return Set.of(
            ASSETS
        );
    }
}
