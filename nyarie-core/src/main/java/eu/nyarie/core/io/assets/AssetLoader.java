package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;

import java.util.List;

/// Interface defining a method for loading a single asset file defined in [AssetPaths]
/// and returning its result mapped to the respective class.
public interface AssetLoader {

    List<? extends Asset> loadFile(AssetPath path);

}
