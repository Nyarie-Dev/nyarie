package eu.nyarie.core.io;

import lombok.Getter;

import java.nio.file.Path;

/// Defines the subpaths of the installation directory
public enum InstallationDirectorySubpath {
    ASSETS(Path.of("assets")),
    ;

    @Getter
    private final Path subpath;

    InstallationDirectorySubpath(Path subpath) {
        this.subpath = subpath;
    }
}
