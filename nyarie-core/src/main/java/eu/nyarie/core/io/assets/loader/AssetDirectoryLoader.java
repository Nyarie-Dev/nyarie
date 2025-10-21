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
        loadAssetsUsingMethod(assetFileLoader::loadAssetFile);
        val uwu = assetFileLoader.loadAssetFile(AssetPaths.REGIONS);
        return null;
    }

    LoadedAssetDirectory fromFileSystemWithClasspathFallback() {
        return null;
    }

    private <T extends AssetDto<?>> void loadAssetsUsingMethod(Function<AssetFilePath<?>, Optional<T>> loaderFunction) {
        var regions = loaderFunction.apply(AssetPaths.REGIONS);
    }
}
