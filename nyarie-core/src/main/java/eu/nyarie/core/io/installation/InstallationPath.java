package eu.nyarie.core.io.installation;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

/// Defines the paths of the installation directory ([ROOT][#ROOT]) and its subdirectories
///
/// For more information on how the [ROOT][#ROOT] installation path is determined and how it can be configured,
/// see [InstallationPathResolver].
///
/// @see InstallationPathResolver
@Slf4j
public enum InstallationPath {
    ROOT(new InstallationPathResolver().determineInstallationDirectoryPath()),
    ASSETS(ROOT.path.resolve("assets")),
    ;

    @Getter
    private final Path path;

    InstallationPath(Path path) {
        this.path = path;
    }
}
