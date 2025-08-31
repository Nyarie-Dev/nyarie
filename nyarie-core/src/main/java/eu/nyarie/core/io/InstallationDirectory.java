package eu.nyarie.core.io;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/// Class that handles all things regarding the engine's **installation directory**
///
/// When creating an instance of this class, the location of the engine's `.jar` file will be located.
///
/// Then, if they do not already exist, the engine's required
/// subdirectories will be created. These include:
///
/// - `/assets`
///
/// This class then provides an interfaces for interacting with the data in the installation directory.
@Slf4j
public class InstallationDirectory {

    private final Path path;

    public InstallationDirectory() {
        log.debug("Initializing engine's installation path");

        log.trace("Locating jar location");
        this.path = Path.of(InstallationDirectory.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        log.debug("Located jar file location: {}", path);

        log.info("Initializing installation directory: {}", path);
        log.info("Checking if all subdirectories exist...");

        val subDirectories = Arrays.stream(InstallationDirectorySubpath.values())
                .map(InstallationDirectorySubpath::getSubpath)
                .toList();

        log.info("Required directories are:");
        log.info("|");
        subDirectories.forEach(subpath -> {
            log.info("|-- /{}", subpath);
        });


        subDirectories.forEach(subpath -> {
            val combinedPath = path.resolve(InstallationDirectorySubpath.ASSETS.getSubpath());
            log.debug("Checking if '{}' subpath exists: {}", subpath, combinedPath);
            if(Files.notExists(combinedPath)) {
                log.debug("Subpath '/{}' does not exist - creating it", subpath);
                try {
                    Files.createDirectories(combinedPath);
                } catch (IOException e) {
                    //TODO: wrap in own exception
                    throw new RuntimeException(e);
                }
                log.info("Created missing '/{}' directory", subpath);
            }
        });

        log.info("All subdirectories exist");

        log.info("Finished initialization of installation directory");
    }

    /// Gets the [Path] of the engine's installation directory. This is equivalent to the location where the `.jar` file containing the engine was executed.
    /// @return The [Path] of the installation directory.
    public Path getPath() {
        return path;
    }

    /// Gets the [Path] of the `/assets` directory inside the installation directory.
    ///
    /// This is equal to the [assets path][InstallationDirectorySubpath#ASSETS] appended to the [installation directory path][#getPath()]
    /// @return The [Path] of the `/assets` directory inside the installation directory.
    public Path getAssetsPath() {
        return path.resolve(InstallationDirectorySubpath.ASSETS.getSubpath());
    }
}
