package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.installation.InstallationDirectory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

/// Record that represents all loaded assets for the engine, without
/// the assets being merged yet.
///
/// This corresponds to the assets of the [InstallationDirectory], as well as all the loaded mods.
@Slf4j
record AssetContext(LoadedAssetDirectory installationAssets, List<LoadedAssetDirectory> modAssets) {

    /// Counts how many asset files have been loaded into this instance.
    /// Sums the [LoadedAssetDirectory#count()] of all loaded directories and returns the result.
    /// @return The total amount of loaded asset files of this instance.
    int totalAssetCount() {
        val installationCount = installationAssets.count();
        val modCount = modAssets.stream().mapToInt(LoadedAssetDirectory::count).sum();
        return installationCount + modCount;
    }


    /// Merges all the loaded assets together into a single [MergedAssetContext].
    ///
    /// Currently, only the assets of the installation directory are used. Asset merging will
    /// be implemented at a later point in: [Merging of loaded assets #5](https://github.com/Nyarie-Dev/nyarie/issues/5)
    MergedAssetContext applyMerge() {
        log.debug("Applying merge on AssetContext with {} loaded assets", totalAssetCount());
        return new MergedAssetContext(
                installationAssets.regions(),
                installationAssets.terrainTypes()
        );
    }
}