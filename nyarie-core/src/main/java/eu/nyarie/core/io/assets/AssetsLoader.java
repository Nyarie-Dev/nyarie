package eu.nyarie.core.io.assets;

import eu.nyarie.core.io.installation.InstallationDirectory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Files;

/// Class for loading the data from the asset JSON files (for example `regions.json`)
///
/// First, the [assets path][InstallationDirectory#getAssetsPath()] of the [InstallationDirectory] is searched for the asset files. <br>
/// If no file for the asset could be found, then the classpath is searched in the `assets` package.
///
/// **This means that asset files inside the [InstallationDirectory] have a higher priority over the ones on the classpath.**
///
/// The exact paths and filenames of the asset files are defined in [AssetPath].
@Slf4j
public class AssetsLoader {

    public static void loadDataFromJson() {
        val installationDirectory = new InstallationDirectory();
        val assetDirectoryPath = installationDirectory.getAssetsPath();
        val assetPaths = AssetPath.getSubpaths();

        log.debug("Checking if all required files are present");
        assetPaths
                .forEach(path -> {
                    log.trace("Creating assetDirectoryPath for file: {}", path);
                    val filePath = assetDirectoryPath.resolve(path);
                    log.debug("Checking if {} is present", filePath);
                    if(Files.notExists(filePath)) {
                        log.error("Could not find asset file: {}", filePath);
                    }
                });
        //TODO check if all files exist
    }
}
