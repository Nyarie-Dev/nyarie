package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.LoadedAssetDirectory;
import eu.nyarie.core.io.installation.InstallationDirectory;
import lombok.val;

import java.util.List;

/// Record that represents all loaded assets for the engine, without
/// the assets being merged yet.
///
/// This corresponds to the assets of the [InstallationDirectory], as well as all the loaded mods.
record AssetContext(LoadedAssetDirectory installationAssets, List<LoadedAssetDirectory> modAssets) {

    /// Counts how many asset files have been loaded into this instance.
    /// Sums the [LoadedAssetDirectory#count()] of all loaded directories and returns the result.
    /// @return The total amount of loaded asset files of this instance.
    int totalAssetCount() {
        val installationCount = installationAssets.count();
        val modCount = modAssets.stream().mapToInt(LoadedAssetDirectory::count).sum();
        return installationCount + modCount;
    }

}