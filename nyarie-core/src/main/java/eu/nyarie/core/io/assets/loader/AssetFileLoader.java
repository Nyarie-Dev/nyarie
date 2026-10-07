package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.JsonFileLoader;
import eu.nyarie.core.io.assets.AssetFileDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/// Class that loads a single asset file and converts it into the respective
/// class.
@Slf4j
public class AssetFileLoader {

    private final JsonFileLoader jsonFileLoader;

    public AssetFileLoader(JsonFileLoader jsonFileLoader) {
        this.jsonFileLoader = jsonFileLoader;
    }

    /// Loads an asset file from the file system.
    ///
    /// The final [Path] where the asset file will be searched for is made from `basePath` + `assetFilePath`.<br>
    /// For example, if the `basePath` is set to `/nyarie` and the `assetFilePath` is [AssetPaths#REGIONS], then the
    /// path of the asset file will be:
    /// ```text
    /// /nyarie/assets/map/region.json
    /// ```
    /// @param basePath The path where the asset should be searched in the file system.
    /// @param assetFilePath The [AssetFilePath] of the asset that should be loaded.
    /// @return [Optional] containing the loaded [AssetFileDto], or [Optional#empty()] if the asset file does not exist.
    public <T extends AssetFileDto<?>> Optional<T> fromFileSystem(Path basePath, AssetFilePath<T> assetFilePath) {
        val path = basePath.resolve(assetFilePath.getPath());
        log.debug("Loading asset file for class '{}': {}", assetFilePath.getAssetClass().getSimpleName(), path);

        if(Files.notExists(path)) {
            log.debug("Asset file '{}' was not found, returning empty optional", path);
            return Optional.empty();
        }

        return jsonFileLoader.readAsset(path, assetFilePath.getAssetClass(), () -> {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                log.warn("No InputStream could be created for path '{}'", path);
                log.warn("Caused by {}: {}", e.getClass().getSimpleName(), e.getMessage());
                log.warn("Passing null to readAsset helper");
                return null;
            }
        });
    }

    /// Loads an asset file from the file system by calling [#fromFileSystem(Path, AssetFilePath)].
    ///
    /// If the asset file does not exist on the file system, the classpath is searched under the
    /// specified `assetFilePath`.
    ///
    /// For example, if the `basePath` is `/home/john/nyarie` and the `assetFilePath` is [AssetPaths#REGIONS],
    /// then the asset file will first be searched on the file system under:
    /// ```text
    /// /home/john/nyarie/assets/map/region.json
    /// ```
    /// If this file does not exist, then the classpath is searched under:
    /// ```text
    /// assets/map/region.json
    /// ```
    /// @param basePath The path where the asset should be searched in the file system.
    /// @param assetFilePath The [AssetFilePath] of the asset that should be loaded.
    /// @return [Optional] containing the loaded [AssetFileDto], or [Optional#empty()] if the asset file does not exist.
    public <T extends AssetFileDto<?>> Optional<T> fromFileSystemWithClasspathFallback(Path basePath, AssetFilePath<T> assetFilePath) {
        val fileSystemAsset = fromFileSystem(basePath, assetFilePath);
        if(fileSystemAsset.isPresent()) {
            log.debug("Asset was loaded using file system - skipping classpath loading");
            return fileSystemAsset;
        }
        else
            log.debug("Asset not present in file system - falling back to classpath loading");

        val path = assetFilePath.getPath();
        log.debug("Loading asset file for class '{}' from classpath resource: {}", assetFilePath.getAssetClass().getSimpleName(), path);

        return jsonFileLoader.readAsset(path, assetFilePath.getAssetClass(), () -> this.getClass().getClassLoader().getResourceAsStream(assetFilePath.getPath().toString()));
    }
}
