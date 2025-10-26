package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// Parent abstract class defining that a type is a serialization DTO of an [Asset].
///
/// These are the classes used for de-/serializing an [Asset].
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AssetDto<T extends Asset> {

}