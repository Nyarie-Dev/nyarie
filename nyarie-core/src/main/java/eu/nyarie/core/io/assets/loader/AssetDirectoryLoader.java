package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.AssetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;
import java.util.Optional;

/// Class responsible for loading an entire `/asset` directory,
/// either from the file system or the JVM's classpath.
@Slf4j
@RequiredArgsConstructor
public class AssetDirectoryLoader {

    private final AssetFileLoader assetFileLoader;

    LoadedAssetDirectory fromFileSystem(Path assetDirectory) {
        log.debug("Loading assets from directory: {}", assetDirectory);
        return loadAssetsUsingMethod(assetFileLoader::fromFileSystem);
    }

    LoadedAssetDirectory fromFileSystemWithClasspathFallback(Path assetDirectory) {
        log.debug("Loading assets with classpath fallback from directory: {}", assetDirectory);
        return loadAssetsUsingMethod(assetFileLoader::fromFileSystemWithClasspathFallback);
    }

    private LoadedAssetDirectory loadAssetsUsingMethod(AssetLoaderFunction loaderFunction) {
        log.trace("Calling AssetFileLoader for regions");
        val regions = loaderFunction.load(AssetPaths.REGIONS).orElseThrow();
        log.trace("Calling AssetFileLoader for terrain types");
        val terrainTypes = loaderFunction.load(AssetPaths.TERRAIN_TYPES).orElseThrow();

        val loadedDirectory = new LoadedAssetDirectory(regions, terrainTypes);
        log.debug("Finished loading asset directory");
        return loadedDirectory;
    }

    @FunctionalInterface
    interface AssetLoaderFunction {
        <T extends AssetDto<?>> Optional<T> load(AssetFilePath<T> assetFilePath);
    }
}
