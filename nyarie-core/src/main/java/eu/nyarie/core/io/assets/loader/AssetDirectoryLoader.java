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
        return loadAssetsUsingMethod(assetDirectory, assetFileLoader::fromFileSystem);
    }

    LoadedAssetDirectory fromFileSystemWithClasspathFallback(Path assetDirectory) {
        log.debug("Loading assets with classpath fallback from directory: {}", assetDirectory);
        return loadAssetsUsingMethod(assetDirectory, assetFileLoader::fromFileSystemWithClasspathFallback);
    }

    private LoadedAssetDirectory loadAssetsUsingMethod(Path basePath, AssetLoaderFunction loaderFunction) {
        log.trace("Calling AssetFileLoader for regions");
        val regions = loaderFunction.load(basePath, AssetPaths.REGIONS);
        log.trace("Calling AssetFileLoader for terrain types");
        val terrainTypes = loaderFunction.load(basePath, AssetPaths.TERRAIN_TYPES);

        val loadedDirectory = new LoadedAssetDirectory(regions, terrainTypes);
        log.debug("Finished loading asset directory");
        return loadedDirectory;
    }

    @FunctionalInterface
    interface AssetLoaderFunction {
        <T extends AssetDto<?>> Optional<T> load(Path basePath, AssetFilePath<T> assetFilePath);
    }
}
