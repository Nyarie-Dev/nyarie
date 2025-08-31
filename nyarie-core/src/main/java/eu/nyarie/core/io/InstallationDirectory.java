package eu.nyarie.core.io;

import eu.nyarie.core.exception.data.ConstDataLoadingException;
import eu.nyarie.core.exception.data.ConstDataNotFoundException;
import eu.nyarie.core.io.assets.AssetsLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

/// Class that handles all things regarding the engine's **installation directory**
///
/// ### Installation Directory Location
///
/// When creating an instance of this class, the location of the installation directory will be determined.
/// By default, this will be the same directory that the `.jar` executable is located in.
/// The default path can be overwritten by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.installation.path` Java System property
/// 2. Setting a `NYARIE_CORE_INSTALLATION_PATH` environment variable
///
/// The values must contain the **absolute path** of the directory in which the asset JSON files are located in.
///
/// ### Creation of required subdirectories
///
/// After the location of the installation directory, the engine's required
/// subdirectories, if they do not already exist, will be created. These include:
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
        this.path = getConfiguredPath();
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

    private static Path getConfiguredPath() {
        val SYSTEM_PROPERTY_NAME = "eu.nyarie.core.installation.path";
        val ENV_NAME = "NYARIE_CORE_INSTALLATION_PATH";
        val DEFAULT_PATH = AssetsLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        val systemPropertyPath = Optional.ofNullable(System.getProperty(SYSTEM_PROPERTY_NAME));
        val envPath = Optional.ofNullable(System.getenv(ENV_NAME));

        val chosenPathStr = systemPropertyPath
                .orElse(envPath
                        .orElse(DEFAULT_PATH));

        val path = Paths.get(chosenPathStr);

        systemPropertyPath.ifPresentOrElse(
                _ -> log.info("Asset path was set using system property value: {}", path),
                () -> envPath.ifPresentOrElse(
                        _ -> log.info("Asset path was set using environment variable value: {}", path),
                        () -> log.info("No configured asset path found - using default: {}", DEFAULT_PATH)
                ));

        log.debug("Checking if path exists: {}", path);
        if(Files.notExists(path)) {
            log.error("Directory could not be found: {}", path);
            val fileNotFoundException = new FileNotFoundException("Directory could not be found: %s".formatted(path));
            throw ConstDataNotFoundException.constDataDirectoryNotFound(path.toString(), fileNotFoundException);
        }

        log.debug("Checking if path is directory: {}", path);
        if(!Files.isDirectory(path)) {
            log.error("Configured path is no directory: {}", path);
            throw ConstDataLoadingException.pathIsNoDirectory(path.toString());
        }

        return path;
    }
}
