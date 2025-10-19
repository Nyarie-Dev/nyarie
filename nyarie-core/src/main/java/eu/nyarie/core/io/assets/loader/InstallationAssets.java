package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.installation.InstallationDirectory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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

    public void loadDataFromJson() {
        log.info("Loading installation assets...");
        val installationDirectory = new InstallationDirectory();
        val assetPaths = AssetPaths.getSubpaths();

        log.trace("Initializing asset loaders");
        val classpathLoader = new ClasspathAssetLoader();
        val filesystemLoader = new FileSystemAssetLoader(installationDirectory.getRootDirectory());

        assetPaths.forEach(assetPath -> {
            log.trace("Calling FileSystemAssetLoader.loadAssetFile");

            val asset = filesystemLoader.loadAssetFile(assetPath);
            if(asset.isPresent()) {
                val assetList = asset.get();
                log.info("Loaded {} asset from installation directory file: {}", assetList.size(), assetPath.getPath());
            }
            else {
                log.debug("Asset not found in file system: {}", assetPath);
                log.debug("Searching in classpath");
                val classpathAsset = classpathLoader.loadAssetFile(assetPath);

                classpathAsset.ifPresentOrElse(
                        assetList -> log.info("Loaded {} asset from classpath file: {}", assetList.size(), assetPath.getPath()),
                        () -> {
                            log.warn("Could not find an asset file for asset: {}", assetPath.getPath());
                            log.warn("If this is intentional, ignore this warning");
                        }
                );
            }
        });
    }
}
