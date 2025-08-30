package eu.nyarie.core.data;

import eu.nyarie.core.domain.constant.map.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/// Class which holds all the {@link Region Regions} of the playable map.<br>
/// The regions are loaded from the `regions.json` data file.
///
/// For more information on how to set up assets, see {@link AssetsLoader}.
/// @see AssetsLoader
public class RegionsAssets {

    private static final List<Region> regions = new ArrayList<>(200);

    /// Returns an unmodifiable {@link List} containing all the loaded regions.
    /// @return An unmodifiable {@link List} containing all the loaded regions.
    public static List<Region> getAll() {
        return Collections.unmodifiableList(regions);
    }

    /// Finds the region with the passed `id`.
    /// @param id The {@link Region#getId()} to query for
    /// @return An {@link Optional} containing the found region. {@link Optional#empty()} if no region was found.
    public static Optional<Region> byId(String id) {
        return regions.stream()
                .filter(region -> region.getId().equals(id))
                .findAny();

    }

    /// Loads the passed list of {@link Region Regions} into asset memory
    static void load(List<Region> regions) {
        RegionsAssets.regions.addAll(regions);
    }
}
