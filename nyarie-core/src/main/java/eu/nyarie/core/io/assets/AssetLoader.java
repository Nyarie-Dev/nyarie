package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;

import java.util.List;

/// Interface defining a method for loading a single asset file defined in [AssetPaths]
/// and returning its result mapped to the respective class.
interface AssetLoader {

    /// Loads the assets from the passed [asset file path][AssetFilePath] and returns a list
    /// of the assets cast to their respective [Asset] class.
    /// @param assetFilePath The path to the asset file. Must be an existing file.
    /// @throws AssetNotFoundException If the asset file could not be found.
    <T extends Asset> List<T> loadAssetFile(AssetFilePath<T> assetFilePath);

}
