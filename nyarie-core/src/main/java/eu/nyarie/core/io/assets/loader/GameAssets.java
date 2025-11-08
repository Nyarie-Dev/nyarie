package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.io.installation.InstallationDirectory;
import eu.nyarie.core.util.DurationUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/// Holds the lists containing the game's assets mapped to their respective
/// [domain classes][eu.nyarie.core.domain.constant.Asset].
@Slf4j
public class GameAssets {

    private final List<Region> regions;
    private final List<TerrainType> terrainTypes;

    GameAssets(List<Region> regions, List<TerrainType> terrainTypes) {
        this.regions = new ArrayList<>(regions);
        this.terrainTypes = new ArrayList<>(terrainTypes);
    }

    /// Loads all the game's assets into memory, merges them, and returns the
    /// [GameAssets] instance containing the assets.
    public static GameAssets load(InstallationDirectory installationDirectory, Set<AssetLoader.ModDirectory> modDirectories) {
        log.info("Starting loading process of game's assets");
        val nowInstant = Instant.now();
        val now = System.nanoTime();

        val assetFileLoader = new AssetFileLoader();
        val assetDirectoryLoader = new AssetDirectoryLoader(assetFileLoader);
        val assetLoader = new AssetLoader(assetDirectoryLoader, installationDirectory, modDirectories);

        val assetContext = assetLoader.loadAssets();
        val mergedAssetContext = assetContext.applyMerge();
        val gameAssets = mergedAssetContext.mapToDomain();

        val finished = Instant.now();
        val diff = nowInstant.until(finished);
        log.info("Finished loading assets in {}", DurationUtils.toReadableString(diff));
        return null;
    }
}
