package eu.nyarie.core.io.appdata;

import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.installation.exception.InstallationDirectoryException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/// Class representing the Engine's Application Data Directory.
///
/// By default, the AppData directory is created on the following path:
/// - Windows: `C:\Users\\<username>\AppData\Roaming\nyarie`
/// - Linux/UNIX: `/home/<username>/.local/share/nyarie`
/// - macOS: `/home/<username>/Library/Application Support/nyarie`
///
/// The location of the AppData directory can be configured by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.appdata.path` Java System property
/// 2. Setting a `NYARIE_CORE_APPDATA_PATH` environment variable
@Slf4j
public final class AppDataDirectory {

    private final AppDataPaths PATHS;

    AppDataDirectory() {
        log.debug("Initializing engine's app data directory");

        log.trace("Initializing AppDataPaths");
        this.PATHS = new AppDataPaths();

        log.trace("Determining path for app data directory");
        val rootPath = PATHS.ROOT;
        log.debug("Determined app data location: {}", rootPath);

        log.info("Initializing app data directory: {}", rootPath);
        log.info("Checking if all subdirectories exist...");

        val subDirectories = PATHS.getSubpaths();

        log.info("Required directories are:");
        log.info("|");
        subDirectories.forEach(subpath -> {
            log.info("|-- {}", subpath);
        });


        subDirectories.forEach(subpath -> {
            log.debug("Checking if subpath exists: {}", subpath);
            if(Files.notExists(subpath)) {
                log.debug("Subpath '{}' does not exist - creating it", subpath);
                try {
                    Files.createDirectories(subpath);
                } catch (IOException e) {
                    LogBlock.withLogger(log)
                            .error("""
                                    ERROR CREATING APP DATA DIRECTORY:
                                    Could not create subdirectory: {}
                                    
                                    Reason: {}
                                    """, subpath, e.getMessage());
                    throw InstallationDirectoryException.couldNotCreateSubdirectory(subpath.toString(), e);
                }
                log.info("Created missing directory: '{}'", subpath);
            }
        });

        log.info("All subdirectories exist");
        log.info("Finished initialization of app data directory");
    }

    /// Gets the [Path] of the app data directory. See [AppDataDirectory] for the default values.
    /// @return The [Path] of the app data directory.
    public Path getRootDirectory() {
        return PATHS.ROOT;
    }

    /// Gets the [Path] of the `/mods` directory inside the app data directory.
    ///
    /// This is equal to the [mods path][AppDataPaths#MODS] appended to the [app data directory path][#getRootDirectory()]
    /// @return The [Path] of the `/mods` directory inside the app data directory.
    public Path getModsDirectory() {
        return PATHS.MODS;
    }
}
