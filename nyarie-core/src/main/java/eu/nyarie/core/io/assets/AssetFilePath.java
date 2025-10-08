package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;

import java.nio.file.Path;

/// Class defining a [Path] of an asset file inside an asset directory.
///
/// All possible file paths are defined in [AssetPaths].
public class AssetFilePath<T extends Asset> {

    private final Path delegate;
    private final Class<T> assetClass;

    AssetFilePath(Path path, Class<T> assetClass) {
        this.delegate = path;
        this.assetClass = assetClass;
    }

    public Class<T> getAssetClass() {
        return assetClass;
    }

    /// Gets the actual underlying [Path] that was passed
    /// on construction of the object.
    public Path getDelegate() {
        return delegate;
    }
}
