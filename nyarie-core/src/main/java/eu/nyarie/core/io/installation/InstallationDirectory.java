package eu.nyarie.core.io.installation;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/// Class that allows interaction with the engine's **installation directory**
///
/// When a new instance of this class is initialized,
/// all the necessary directories inside the **installation directory** are created and set up.
///
/// ### Installation Directory Paths
///
/// The paths of the installation directory and its subdirectories are defined in the [InstallationPath] class.
///
/// ### Creation of required subdirectories
///
/// After the location of the installation directory is retrieved, the engine's required
/// subdirectories, if they do not already exist, will be created.
/// These include:
///
/// - `/assets`
///
/// This class then provides methods for interacting with the data in the installation directory.
/// @see InstallationPath
@Slf4j
public class InstallationDirectory {

    public InstallationDirectory() {
        log.debug("Initializing engine's installation path");

        log.trace("Locating jar location");
        val rootPath = InstallationPath.ROOT;
        log.debug("Located jar file location: {}", rootPath);

        log.info("Initializing installation directory: {}", rootPath);
        log.info("Checking if all subdirectories exist...");

        val subDirectories = InstallationPath.getSubdirectories();

        log.info("Required directories are:");
        log.info("|");
        subDirectories.forEach(subpath -> {
            log.info("|-- /{}", subpath);
        });


        subDirectories.forEach(subpath -> {
            val combinedPath = rootPath.resolve(InstallationPath.ASSETS);
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
    public Path getRootPath() {
        return InstallationPath.ROOT;
    }

    /// Gets the [Path] of the `/assets` directory inside the installation directory.
    ///
    /// This is equal to the [assets path][InstallationPath#ASSETS] appended to the [installation directory path][#getRootPath()]
    /// @return The [Path] of the `/assets` directory inside the installation directory.
    public Path getAssetsPath() {
        return InstallationPath.ASSETS;
    }
}
