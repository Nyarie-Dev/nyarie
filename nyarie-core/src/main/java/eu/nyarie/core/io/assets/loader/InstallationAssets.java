package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.installation.InstallationDirectory;
import eu.nyarie.core.util.serialization.NyarieObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;

/// Class responsible for loading and holding the data read from the asset JSON files
/// (for example `regions.json`) that were shipped with the engine's installation.<br>
/// This explicitly refers to assets that were shipped with the classpath resources of the engine's `jar` file,
/// or assets that are located in the [assets directory][InstallationDirectory#getAssetsDirectory()] of the [InstallationDirectory].
///
/// First, the [assets directory][InstallationDirectory#getAssetsDirectory()] of the [InstallationDirectory] is searched for the asset files. <br>
/// If no file for the asset could be found, then the classpath is searched in the `assets` directory.
///
/// <b>This means that asset files inside the [InstallationDirectory] have a higher priority over the ones on the classpath.</b>
///
/// The exact paths and filenames of the asset files are defined in [AssetPaths].
@Slf4j
public class InstallationAssets {

    public static void loadDataFromJson() {
        val installationDirectory = new InstallationDirectory();
        val assetPaths = AssetPaths.getSubpaths();

        val classpathLoader = new ClasspathAssetLoader();
        val filesystemLoader = new FileSystemAssetLoader(installationDirectory.getRootDirectory());

        assetPaths.forEach(assetPath -> {
            log.trace("Calling FileSystemAssetLoader.loadAssetFile");

            val asset = filesystemLoader.loadAssetFile(assetPath);
            if(asset.isEmpty()) {
                log.debug("Asset not found in file system: {}", assetPath);
                log.debug("Searching in classpath");
                val classpathAsset = classpathLoader.loadAssetFile(assetPath);

                classpathAsset.ifPresentOrElse(
                        assetList -> log.info("Loaded {} asset from classpath: {}", assetList.size(), assetPath.getPath()),
                        () -> log.warn("Could not find an asset file for asset: {}", assetPath.getPath())
                );
            }
        });
        //TODO check if all files exist
    }

    private static void readResource(String resource) {
        log.info("Searching classpath for asset: {}", resource);
        try(val resourceStream = InstallationAssets.class.getClassLoader().getResourceAsStream(resource)) {
            if(resourceStream == null) {
                log.error("Could not find asset file in classpath: {}", resource);
                return;
            }
            val objectMapper = new NyarieObjectMapper().getInstance();
            val region = objectMapper.readValue(resourceStream, Region.class);
            val jsonString = objectMapper.writeValueAsString(region);
            log.info("Read value from classpath assets {}: {}", resource, jsonString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static record Region(String hello) {}
}
