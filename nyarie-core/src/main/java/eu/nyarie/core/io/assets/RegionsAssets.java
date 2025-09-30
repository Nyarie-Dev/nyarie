package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.map.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/// Class which holds all the {@link Region Regions} of the playable map.<br>
/// The regions are loaded from the `regions.json` data file.
///
/// For more information on how to set up assets, see {@link InstallationAssets}.
/// @see InstallationAssets
public final class RegionsAssets extends Assets<Region> {

    private final List<Region> regions = new ArrayList<>(200);

    public RegionsAssets(List<Region> assets) {
        super(assets);
    }

    /// Returns an unmodifiable {@link List} containing all the loaded regions.
    /// @return An unmodifiable {@link List} containing all the loaded regions.
    public List<Region> getAll() {
        return Collections.unmodifiableList(regions);
    }

    /// Finds the region with the passed `id`.
    /// @param id The {@link Region#getId()} to query for
    /// @return An {@link Optional} containing the found region. {@link Optional#empty()} if no region was found.
    public Optional<Region> byId(String id) {
        return regions.stream()
                .filter(region -> region.getId().equals(id))
                .findAny();

    }

    /// Loads the passed list of {@link Region Regions} into asset memory
    void load(List<Region> regions) {
        this.regions.addAll(regions);
    }
}
