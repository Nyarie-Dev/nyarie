package eu.nyarie.core.data;

import eu.nyarie.core.exception.data.ConstDataLoadingException;
import eu.nyarie.core.exception.data.ConstDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/// Class for loading the data from the const data JSON files (for example `regions.json`)
///
/// By default, the files will be searched inside the same directory that the jar executable is located in.
/// The default path can be overwritten by:
/// 1. Setting the `eu.nyarie.core.data.path` Java System property
/// 2. Setting a `NYARIE_CORE_DATA_PATH` environment variable
///
/// The values must contain the **absolute path** of the directory in which the const data JSON files are located in.
///
/// If no file with the required name was found, then the classpath is searched.
@Slf4j
public class ConstDataLoader {


    public static void loadDataFromJson() {
        val path = getConfiguredPath();

        log.debug("Checking if path exists: {}", path);
        if(Files.notExists(path)) {
            log.error("Directory could not be found: {}", path);
            val fileNotFoundException = new FileNotFoundException("Directory could not be found: %s".formatted(path));
            throw ConstDataNotFoundException.constDataDirectoryNotFound(path.toString(), fileNotFoundException);
        }

        log.debug("Checking if path is directory: {}", path);
        if(Files.isDirectory(path)) {
            log.error("Configured path is no directory: {}", path);
            throw ConstDataLoadingException.pathIsNoDirectory(path.toString());
        }

        val filenameEnums = Arrays.asList(ConstDataFileNames.values());
        val constDataFiles = filenameEnums.stream()
                .map(filename -> Map.of(filename, new File(path + filename.getFilename())));

        log.debug("Checking if all required files are present");
        //TODO check if all files exist
    }

    private static Path getConfiguredPath() {
        val SYSTEM_PROPERTY_NAME = "eu.nyarie.core.data.path";
        val ENV_NAME = "NYARIE_CORE_DATA_PATH";
        val DEFAULT_PATH = ConstDataLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        val systemPropertyPath = Optional.ofNullable(System.getProperty(SYSTEM_PROPERTY_NAME));
        val envPath = Optional.ofNullable(System.getenv(ENV_NAME));

        systemPropertyPath.ifPresentOrElse(
                path -> log.info("Const data path was set using system property value: {}", path),
                () -> envPath.ifPresentOrElse(
                        path -> log.info("Const data path was set using environment variable value: {}", path),
                        () -> log.info("Using default const data path: {}", DEFAULT_PATH)
                ));

        val path = systemPropertyPath
                .orElse(envPath
                        .orElse(DEFAULT_PATH));

        return Paths.get(path);
    }
}
