package eu.nyarie.core.io.assets;

import eu.nyarie.core.util.PathDecorator;

import java.nio.file.Path;

/// Class defining a [Path] of an asset file inside an asset directory.
///
/// All possible file paths are defined in [AssetPaths].
public class AssetFilePath extends PathDecorator {

    AssetFilePath(Path path) {
        super(path);
    }
}
