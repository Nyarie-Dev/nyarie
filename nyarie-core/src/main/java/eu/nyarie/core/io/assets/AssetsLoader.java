package eu.nyarie.core.io.assets;

import eu.nyarie.core.exception.data.ConstDataLoadingException;
import eu.nyarie.core.exception.data.ConstDataNotFoundException;
import eu.nyarie.core.io.InstallationDirectory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

/// Class for loading the data from the asset JSON files (for example `regions.json`)
///
/// First, the [assets path][InstallationDirectory#getAssetsPath()] of the [InstallationDirectory] is searched for the asset files. <br>
/// If no file for the asset could be found, then the classpath is searched in the `assets` package.
///
/// **This means that asset files inside the [InstallationDirectory] have a higher priority over the ones on the classpath.**
///
/// The exact paths and filenames of the asset files are defined in [AssetsFileNames].
@Slf4j
public class AssetsLoader {

    public static void loadDataFromJson() {
        val path = getConfiguredPath();

        val filenameEnums = Arrays.asList(AssetsFileNames.values());

        log.debug("Checking if all required files are present");
        filenameEnums.stream()
                .map(AssetsFileNames::getFilename)
                .forEach(filename -> {
                    log.trace("Creating path for file: {}", filename);
                    val filePath = Paths.get(path.toString(), filename);
                    log.debug("Checking if {} is present", filePath);
                    if(Files.notExists(filePath)) {
                        log.error("Could not find asset file: {}", filePath);
                    }
                });
        //TODO check if all files exist
    }

    private static Path getConfiguredPath() {
        val SYSTEM_PROPERTY_NAME = "eu.nyarie.core.data.path";
        val ENV_NAME = "NYARIE_CORE_DATA_PATH";
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
