package eu.nyarie.core.io.init;

import java.nio.file.Path;

/// Class defining a [Path] of an init file inside an init directory.
///
/// All possible file paths are defined in [InitFilePaths].
public class InitFilePath<T extends InitFileContentDto<?>> {

    private final Path path;
    private final Class<T> dtoClass;

    InitFilePath(Path path, Class<T> dtoClass) {
        this.path = path;
        this.dtoClass = dtoClass;
    }

    public Class<T> getDtoClass() {
        return dtoClass;
    }

    /// Gets the actual underlying [Path] that was passed
    /// on construction of the object.
    public Path getPath() {
        return path;
    }
}
