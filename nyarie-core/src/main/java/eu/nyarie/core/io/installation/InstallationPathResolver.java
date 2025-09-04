package eu.nyarie.core.io.installation;

import eu.nyarie.core.io.assets.exception.AssetLoadingException;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private final InstallationPathConfigReader configReader;

    public InstallationPathResolver() {
        this(new InstallationPathConfigReader());
    }

    /// This constructor is only used for testing-purposes. Instead, use the [#InstallationPathResolver()] constructor.
    InstallationPathResolver(InstallationPathConfigReader configReader) {
        log.debug("Initializing new {}", InstallationPathResolver.class.getSimpleName());
        this.configReader = configReader;
    }

    public Path determineInstallationDirectoryPath() {
        val DEFAULT_PATH = getJarLocation();

        val systemPropertyPath = configReader.getSystemPropertyValue();
        val envPath = configReader.getEnvVarValue();

        val chosenPathStr = systemPropertyPath
                .orElse(envPath
                        .orElse(DEFAULT_PATH));

        val path = Paths.get(chosenPathStr);

        systemPropertyPath.ifPresentOrElse(
                _ -> log.info("Installation path was set using system property value: {}", path),
                () -> envPath.ifPresentOrElse(
                        _ -> log.info("Installation path was set using environment variable value: {}", path),
                        () -> log.info("No custom installation path set - using default: {}", DEFAULT_PATH)
                ));

        log.debug("Checking if path is absolute");
        if(!path.isAbsolute()) {
            log.error("|=======================================================================================");
            log.error("| ERROR SETTING UP INSTALLATION DIRECTORY:");
            log.error("| Configured installation path must be absolute - was: {}", path);
            log.error("|");
            log.error("| Tip: The path must contain the root element");
            log.error("| On Unix systems (Linux/MacOS), this is '/'");
            log.error("| \t\tExample path: '/home/john/nyarie'");
            log.error("|");
            log.error("| On Windows, this is your target partition (most commonly 'C:')");
            log.error("| Windows system also require backslashes ('\\') instead of forward slashes ('/')");
            log.error("| \t\tExample path: 'C:\\Users\\john\\nyarie'");
            log.error("|");
            log.error("|=======================================================================================");
            throw AssetNotFoundException.configuredPathNotAbsolute(path.toString());
        }

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

    private String getJarLocation() {
        try {
            return Path.of(InstallationPathResolver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
