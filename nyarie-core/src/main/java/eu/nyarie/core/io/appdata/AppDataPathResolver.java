package eu.nyarie.core.io.appdata;

import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.appdata.exception.AppDataDirectoryException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/// Helper Class responsible for determining the path of the engine's **app data directory**.
///
/// ### Default path
/// The default path of the app data directory will be in a subdirectory of the user home directory.
/// The subdirectory however depends on the OS that the engine is running on.<br>
/// Assuming defaults user homes with a user called `john`:
/// - Windows: `C:\Users\john\AppData\Roaming`
/// - Linux/UNIX: `/home/john/.local/share`
/// - macOS: `/Users/john/Library/Application Support`
///
/// ### Configuration
/// The default path is customizable and can be overwritten by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.appdata.path` Java System property
/// 2. Setting the `NYARIE_CORE_APPDATA_PATH` environment variable
///
/// The values must contain the **absolute path** of the directory that should be used.
@Slf4j
final class AppDataPathResolver {

    private static final LogBlock logBlock = LogBlock.withLogger(log);

    private final AppDataPathConfigReader configReader;

    public AppDataPathResolver() {
        this(new AppDataPathConfigReader());
    }

    /// This constructor is only used for testing-purposes. Instead, use the [#AppDataPathResolver()] constructor.
    AppDataPathResolver(AppDataPathConfigReader configReader) {
        log.debug("Initializing new {}", AppDataPathResolver.class.getSimpleName());
        this.configReader = configReader;
    }

    public Path determineInstallationDirectoryPath() {
        val DEFAULT_PATH = getDefaultPath();

        val systemPropertyPath = configReader.getSystemPropertyValue();
        val envPath = configReader.getEnvVarValue();

        val chosenPathStr = systemPropertyPath
                .orElse(envPath
                        .orElse(DEFAULT_PATH));

        val path = Paths.get(chosenPathStr);

        systemPropertyPath.ifPresentOrElse(
                _ -> log.info("App data path was set using system property value: {}", path),
                () -> envPath.ifPresentOrElse(
                        _ -> log.info("App data path was set using environment variable value: {}", path),
                        () -> log.info("No custom app data path set - using default: {}", DEFAULT_PATH)
                ));

        log.debug("Checking if path is absolute");
        if(!path.isAbsolute()) {
            logBlock.withBorderLength(100)
                    .error("""
                    ERROR SETTING UP APP DATA DIRECTORY:
                    Configured app data path must be absolute - was: {}
                    
                    Tip: The path must contain the root element
                    On Unix systems (Linux/MacOS), this is '/'
                            Example path: '/home/john/nyarie'
                    
                    On Windows, this is your target partition (most commonly 'C:')
                    Windows system also require backslashes ('\\') instead of forward slashes ('/')
                            Example path: 'C:\\Users\\john\\nyarie'
                    """, path);
            throw AppDataDirectoryException.configuredPathNotAbsolute(path.toString());
        }

        log.debug("Checking if path exists: {}", path);
        if (Files.notExists(path)) {
            log.error("Directory could not be found: {}", path);
            val fileNotFoundException = new FileNotFoundException("Directory could not be found: %s".formatted(path));
            throw AppDataDirectoryException.directoryNotFound(path.toString(), fileNotFoundException);
        }

        log.debug("Checking if path is directory: {}", path);
        if (!Files.isDirectory(path)) {
            logBlock.error("""
                    ERROR SETTING UP INSTALLATION DIRECTORY:
                    Configured installation path is not a directory: {}
                    """, path);
            throw AppDataDirectoryException.pathIsNoDirectory(path.toString());
        }

        return path;
    }

    private static String getDefaultPath() {
        val userHome = System.getProperty("user.home");
        val os = System.getProperty("os.name");

        log.info("OS: {}", os);
        log.info("User home: {}", userHome);

        return userHome;
    }
}
