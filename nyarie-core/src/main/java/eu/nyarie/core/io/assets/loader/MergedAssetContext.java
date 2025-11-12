package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.io.assets.map.RegionsAsset;
import eu.nyarie.core.io.assets.map.TerrainTypesAsset;
import lombok.val;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// Record that represents the final, merged assets for the engine.
///
/// Result of merging all the assets of an [AssetContext].
record MergedAssetContext(
        Optional<RegionsAsset> regions,
        Optional<TerrainTypesAsset> terrainTypes
) {

    /// ONLY A SKELETON IMPLEMENTATION FOR NOW.
    /// This will be properly implemented in: [Merging of loaded assets #5](https://github.com/Nyarie-Dev/nyarie/issues/5)
    GameAssets mapToDomain() {
        val mappedTerrainTypes = terrainTypes
                .map(TerrainTypesAsset::getData)
                .orElse(List.of())
                .stream()
                .map(tt -> new TerrainType(tt.getId(), tt.getName(), tt.getMovementDuration()))
                .toList();
        val mappedRegions = regions
                .map(RegionsAsset::getData)
                .orElse(List.of())
                .stream()
                .map(region ->
                        new Region(region.getId(), region.getName(), resolveTerrainType(region.getTerrainType(), mappedTerrainTypes), Set.of(), Set.of(), Set.of()))
                .toList();
        return new GameAssets(mappedRegions, mappedTerrainTypes);
    }

    /// Temporary for now, will probably be changed in the future
    private TerrainType resolveTerrainType(String id, List<TerrainType> terrainTypes) {
        return terrainTypes.stream()
                .filter(tt -> id.equals(tt.getId()))
                .findAny()
                .orElse(null);
    }

}