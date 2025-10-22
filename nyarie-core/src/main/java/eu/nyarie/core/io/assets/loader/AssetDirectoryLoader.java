package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.LoadedAssetDirectory;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

/// Class responsible for loading an entire `/asset` directory,
/// either from the file system or the JVM's classpath.
@RequiredArgsConstructor
public class AssetDirectoryLoader {

    private final Path assetDirectory;
    private final AssetFileLoader assetFileLoader;

    LoadedAssetDirectory fromFileSystem() {
        return loadAssetsUsingMethod(assetFileLoader::loadAssetFile);
    }

    LoadedAssetDirectory fromFileSystemWithClasspathFallback() {
        return loadAssetsUsingMethod(assetFileLoader::fromFileSystemWithClasspathFallback);
    }

    private <T extends AssetDto<?>> LoadedAssetDirectory loadAssetsUsingMethod(AssetLoaderFunction loaderFunction) {
        val regions = loaderFunction.load(AssetPaths.REGIONS).orElseThrow();
        val terrainTypes = loaderFunction.load(AssetPaths.TERRAIN_TYPES).orElseThrow();

        return new LoadedAssetDirectory(regions, terrainTypes);
    }

    @FunctionalInterface
    interface AssetLoaderFunction {
        <T extends AssetDto<?>> Optional<T> load(AssetFilePath<T> assetFilePath);
    }
}
