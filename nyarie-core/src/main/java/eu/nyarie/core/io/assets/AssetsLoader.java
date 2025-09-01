package eu.nyarie.core.io.assets;

import eu.nyarie.core.io.InstallationDirectory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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
        val path = installationDirectory.getAssetsPath();
        val filenameEnums = Arrays.asList(AssetPath.values());

        log.debug("Checking if all required files are present");
        filenameEnums.stream()
                .map(AssetPath::getFilename)
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
}
