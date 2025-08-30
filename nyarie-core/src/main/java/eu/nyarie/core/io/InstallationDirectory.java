package eu.nyarie.core.io;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        this.path = Paths.get(InstallationDirectory.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        log.debug("Located jar file location: {}", path);

        log.info("Initializing installation directory: {}", path);
        log.info("Checking if all directories exist...");

        val assetsPath = Paths.get(path.toString(), "assets");
        log.debug("Checking if assets path exists: {}", assetsPath);
        if(Files.notExists(assetsPath)) {
            log.debug("Assets path does not exist - creating it");
            try {
                Files.createDirectories(assetsPath);
            } catch (IOException e) {
                //TODO: wrap in own exception
                throw new RuntimeException(e);
            }
            log.info("Created missing `/assets` directory");
        }
    }

    /// Gets the [Path] of the engine's installation directory. This is equivalent to the location where the `.jar` file containing the engine was executed.
    /// @return The [Path] of the installation directory.
    public Path getPath() {
        return path;
    }
}
