package eu.nyarie.core.io.installation;

import eu.nyarie.core.io.assets.exception.AssetLoadingException;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/// Helper Class responsible for determining the path of the engines **installation directory**.
///
/// By default, the path of the installation directory will be the same directory that the `.jar` executable is located in.
/// The default path is customizable and can be overwritten by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.installation.path` Java System property
/// 2. Setting a `NYARIE_CORE_INSTALLATION_PATH` environment variable
///
/// The values must contain the **absolute path** of the directory in which the asset JSON files are located in.
@Slf4j
final class InstallationPathResolver {

    public InstallationPathResolver() {
        log.debug("Initializing new {}", this.getClass().getSimpleName());
    }

    public Path determineInstallationDirectoryPath() {
        val SYSTEM_PROPERTY_NAME = "eu.nyarie.core.installation.path";
        val ENV_NAME = "NYARIE_CORE_INSTALLATION_PATH";
        val DEFAULT_PATH = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

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
        if (Files.notExists(path)) {
            log.error("Directory could not be found: {}", path);
            val fileNotFoundException = new FileNotFoundException("Directory could not be found: %s".formatted(path));
            throw AssetNotFoundException.assetDirectoryNotFound(path.toString(), fileNotFoundException);
        }

        log.debug("Checking if path is directory: {}", path);
        if (!Files.isDirectory(path)) {
            log.error("Configured path is no directory: {}", path);
            throw AssetLoadingException.pathIsNoDirectory(path.toString());
        }

        return path;
    }
}
