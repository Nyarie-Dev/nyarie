package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.io.assets.Assets;
import eu.nyarie.core.io.assets.loader.InstallationAssets;

import java.util.List;
import java.util.Optional;

/// Class which holds all the {@link Region Regions} of the playable map.<br>
/// The regions are loaded from the `regions.json` data file.
///
/// For more information on how to set up assets, see {@link InstallationAssets}.
/// @see InstallationAssets
public final class RegionsAssets extends Assets<Region, RegionAsset> {

    public RegionsAssets(List<RegionAsset> assets) {
        super(assets);
    }

    /// Finds the region with the passed `id`.
    /// @param id The {@link Region#getId()} to query for
    /// @return An {@link Optional} containing the found region. {@link Optional#empty()} if no region was found.
    public Optional<Region> byId(String id) {
        return this.assets.stream()
                .filter(region -> region.getId().equals(id))
                .findAny();

    }

    @Override
    protected List<Region> convertFromDtos(List<RegionAsset> dtos) {
        return List.of();
    }
}
