package eu.nyarie.core.data;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

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
    }
}
