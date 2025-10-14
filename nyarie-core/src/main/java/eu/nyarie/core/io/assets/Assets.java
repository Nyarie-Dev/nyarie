package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Assets<A extends Asset, T extends AssetDto<A>> {

    protected final List<A> assets;

    /// Creates an instance of this class with the passed `assets`.
    public Assets(List<T> dtos) {
        val assets = this.convertFromDtos(dtos);
        this.assets = new ArrayList<>(assets);
    }

    protected abstract List<A> convertFromDtos(List<T> dtos);

    /// Returns an [unmodifiable list][Collections#unmodifiableList(List)] containing all the assets.
    /// @return an [unmodifiable list][Collections#unmodifiableList(List)] containing all the assets.
    public List<A> getAll() {
        return Collections.unmodifiableList(assets);
    }
}
