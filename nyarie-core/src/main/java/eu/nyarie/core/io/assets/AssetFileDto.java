package eu.nyarie.core.io.assets;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/// Abstract parent class for all types that are representations of an
/// asset file.
///
/// Each asset file has a `data` attribute, which is a list containing the
/// actual assets.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AssetFileDto<T extends AssetDto<?>> {

    private List<T> data;

    @SafeVarargs
    public AssetFileDto(T... data) {
        this.data = Arrays.asList(data);
    }
}