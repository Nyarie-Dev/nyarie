package eu.nyarie.core.io.assets;

import eu.nyarie.core.domain.constant.Asset;

/// Parent abstract class defining that a type is a serialization DTO of an [Asset].
///
/// These are the classes used for de-/serializing an [Asset].
public abstract class AssetDto<T extends Asset> {
}