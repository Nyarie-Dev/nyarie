package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public sealed abstract class Assets<T extends Asset> permits RegionsAssets {

    protected final List<T> assets;

    /// Creates an instance of this class with the passed `assets`.
    public Assets(List<T> assets) {
        this.assets = new ArrayList<>(assets);
    }

    /// Returns an [unmodifiable list][Collections#unmodifiableList(List)] containing all the assets.
    /// @return an [unmodifiable list][Collections#unmodifiableList(List)] containing all the assets.
    public List<T> getAll() {
        return Collections.unmodifiableList(assets);
    }
}
