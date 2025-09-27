package eu.nyarie.core.io.assets;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nyarie.core.io.installation.InstallationDirectory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;

/// Class for loading the data from the asset JSON files (for example `regions.json`)
///
/// First, the [assets directory][InstallationDirectory#getAssetsDirectory()] of the [InstallationDirectory] is searched for the asset files. <br>
/// If no file for the asset could be found, then the classpath is searched in the `assets` directory.
///
/// **This means that asset files inside the [InstallationDirectory] have a higher priority over the ones on the classpath.**
///
/// The exact paths and filenames of the asset files are defined in [AssetPaths].
@Slf4j
public class AssetsLoader {

    public static void loadDataFromJson() {
        val installationDirectory = new InstallationDirectory();
        val assetDirectoryPath = installationDirectory.getRootDirectory();
        val assetPaths = AssetPaths.getSubpaths();

        log.debug("Checking if all required files are present");
        assetPaths.forEach(path -> {
            log.trace("Creating assetDirectoryPath for file: {}", path);
            val filePath = assetDirectoryPath.resolve(path);
            log.debug("Checking if asset file {} exists", filePath);
            if(Files.notExists(filePath)) {
                log.error("Could not find asset file in installation directory: {}", filePath);
                log.error("Searching in classpath");
                readResource(path.toString());
            }
            else {
                log.info("Found asset file {}", filePath);
            }
        });
        //TODO check if all files exist
    }

    private static void readResource(String resource) {
        log.info("Searching classpath for asset: {}", resource);
        try(val resourceStream = AssetsLoader.class.getClassLoader().getResourceAsStream(resource)) {
            if(resourceStream == null) {
                log.error("Could not find asset file in classpath: {}", resource);
                return;
            }
            val objectMapper = new ObjectMapper();
            val region = objectMapper.readValue(resourceStream, Region.class);
            val jsonString = objectMapper.writeValueAsString(region);
            log.info("Read value from classpath assets {}: {}", resource, jsonString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static record Region(String hello) {}
}
