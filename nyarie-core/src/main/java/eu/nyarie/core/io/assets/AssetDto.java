package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/// Parent abstract class defining that a type is a serialization DTO of an [Asset].
///
/// These are the classes used for de-/serializing an [Asset].
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AssetDto<T extends Asset> {

    private List<T> data;

    @SafeVarargs
    public AssetDto(T... data) {
        this.data = Arrays.asList(data);
    }
}