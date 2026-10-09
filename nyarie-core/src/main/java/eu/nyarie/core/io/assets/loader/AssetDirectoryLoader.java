package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.FileContentDto;
import eu.nyarie.core.io.FileLoader;
import eu.nyarie.core.io.FilePath;
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

    private final FileLoader fileLoader;

    LoadedAssetDirectory fromFileSystem(Path assetDirectory) {
        log.debug("Loading assets from directory: {}", assetDirectory);
        return loadAssetsUsingMethod(assetDirectory, fileLoader::jsonFromFileSystem);
    }

    LoadedAssetDirectory fromFileSystemWithClasspathFallback(Path assetDirectory) {
        log.debug("Loading assets with classpath fallback from directory: {}", assetDirectory);
        return loadAssetsUsingMethod(assetDirectory, fileLoader::jsonFromFileSystemWithClasspathFallback);
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
        <T extends FileContentDto<?>> Optional<T> load(Path basePath, FilePath<T> assetFilePath);
    }
}
