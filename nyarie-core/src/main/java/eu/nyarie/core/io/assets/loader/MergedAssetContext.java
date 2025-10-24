package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/// Record that represents the final, merged assets for the engine.
///
/// Result of merging all the assets of an [AssetContext].
record MergedAssetContext(
        RegionAsset regions,
        TerrainTypeAsset terrainTypes
) {

    /// ONLY A SKELETON IMPLEMENTATION FOR NOW.
    /// This will be properly implemented in: [Merging of loaded assets #5](https://github.com/Nyarie-Dev/nyarie/issues/5)
    GameAssets mapToDomain() {
        val mappedTerrainTypes = new ArrayList<TerrainType>(1);
        TerrainType mappedTerrainType = null;
        if(terrainTypes != null) {
            mappedTerrainType = new TerrainType(terrainTypes.getId(), terrainTypes.getName(), terrainTypes.getGetMovementDuration());
            mappedTerrainTypes.add(mappedTerrainType);
        }
        val mappedRegions = new ArrayList<Region>(1);
        if(regions != null)
            mappedRegions.add(new Region(regions.getId(), regions.getName(), mappedTerrainType, Set.of(), Set.of(), Set.of()));

        return new GameAssets(mappedRegions, mappedTerrainTypes);
    }

}