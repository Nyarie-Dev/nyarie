package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;

import java.nio.file.Path;
import java.util.List;

/// Implementation for [AssetLoader] that loads asset files from the
/// file system.
public class FileSystemAssetLoader implements AssetLoader {

    private final Path basePath;

    /// Creates an instance with a given `basePath`. The
    /// assets will be searched in the `/asset` subdirectory of the
    /// `basePath`.
    ///
    /// For example, if the `basePath` is set to `/nyarie`, the assets will be searched
    /// in `/nyarie/assets`
    /// @param basePath The [Path] in which the assets will be searched in.
    public FileSystemAssetLoader(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public <T extends Asset> List<T> loadAssetFile(AssetFilePath<T> assetFilePath) {
        
        return List.of();
    }
}
