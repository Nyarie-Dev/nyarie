package eu.nyarie.core.io.assets.path;

import eu.nyarie.core.util.PathDecorator;

import java.nio.file.Path;

/// Class defining a [Path] that is part of an asset directory.
public class AssetPath extends PathDecorator {

    AssetPath(Path path) {
        super(path);
    }
}
