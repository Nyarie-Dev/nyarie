package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;

import java.util.List;

/// Interface defining a method for loading a single asset file defined in [AssetPaths]
/// and returning its result mapped to the respective class.
public interface AssetLoader {

    /// Loads the assets from the passed [asset file path][AssetFilePath] and returns a list
    /// of the assets cast to their respective [Asset] class.
    /// @param assetFilePath The path to the asset file. Must be an existing file.
    <T extends Asset> List<T> loadFile(AssetFilePath<T> assetFilePath);

}
