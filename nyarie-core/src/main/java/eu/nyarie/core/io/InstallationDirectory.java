package eu.nyarie.core.io;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/// Class that handles all things regarding the engine's **installation directory**
///
/// ### Installation Directory Location
///
/// The location of the installation directory is customizable.
/// By default, it is the location where the project's `.jar` file is located.
///
/// For more information on how to configure the installation path, see [InstallationPath].
/// ### Creation of required subdirectories
///
/// After the location of the installation directory, the engine's required
/// subdirectories, if they do not already exist, will be created.
/// These include:
///
/// - `/assets`
///
/// This class then provides an interfaces for interacting with the data in the installation directory.
///
/// @see InstallationPath
@Slf4j
public class InstallationDirectory {

    private final Path rootPath;

    public InstallationDirectory() {
        log.debug("Initializing engine's installation path");

        log.trace("Locating jar location");
        this.rootPath = InstallationPath.ROOT.getSubpath();
        log.debug("Located jar file location: {}", rootPath);

        log.info("Initializing installation directory: {}", rootPath);
        log.info("Checking if all subdirectories exist...");

        val subDirectories = Arrays.stream(InstallationPath.values())
                .map(InstallationPath::getSubpath)
                .toList();

        log.info("Required directories are:");
        log.info("|");
        subDirectories.forEach(subpath -> {
            log.info("|-- /{}", subpath);
        });


        subDirectories.forEach(subpath -> {
            val combinedPath = rootPath.resolve(InstallationPath.ASSETS.getSubpath());
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
        return rootPath;
    }

    /// Gets the [Path] of the `/assets` directory inside the installation directory.
    ///
    /// This is equal to the [assets path][InstallationPath#ASSETS] appended to the [installation directory path][#getRootPath()]
    /// @return The [Path] of the `/assets` directory inside the installation directory.
    public Path getAssetsPath() {
        return rootPath.resolve(InstallationPath.ASSETS.getSubpath());
    }
}
