package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;
import eu.nyarie.core.util.PathDecorator;

import java.nio.file.Path;

/// Class defining a [Path] of an asset file inside an asset directory.
///
/// All possible file paths are defined in [AssetPaths].
public class AssetFilePath<T extends Asset> extends PathDecorator {

    private final Class<T> assetClass;

    AssetFilePath(Path path, Class<T> assetClass) {
        super(path);
        this.assetClass = assetClass;
    }

    public Class<T> getAssetClass() {
        return assetClass;
    }
}
