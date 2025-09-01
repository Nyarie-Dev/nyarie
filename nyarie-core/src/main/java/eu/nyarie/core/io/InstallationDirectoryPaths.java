package eu.nyarie.core.io;

import eu.nyarie.core.exception.data.ConstDataLoadingException;
import eu.nyarie.core.exception.data.ConstDataNotFoundException;
import eu.nyarie.core.io.assets.AssetsLoader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/// Defines the paths of the installation directory and its subdirectories
///
/// By default, the path of the installation directory will be the same directory that the `.jar` executable is located in.
/// The default path can be overwritten by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.installation.path` Java System property
/// 2. Setting a `NYARIE_CORE_INSTALLATION_PATH` environment variable
///
/// The values must contain the **absolute path** of the directory in which the asset JSON files are located in.
@Slf4j
public enum InstallationDirectoryPaths {
    ASSETS(Path.of("assets")),
    ;

    @Getter
    private final Path subpath;

    InstallationDirectoryPaths(Path subpath) {
        this.subpath = subpath;
    }

    private static Path determineInstallationDirectoryPath() {
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
