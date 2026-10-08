package eu.nyarie.core.io;

import java.nio.file.Path;

/// Class defining a [Path] of a file that should be loaded.
/// Each subclass needs to specify a [FileContentDto] which represents
/// the DTO class into which the file can be deserialized into.
public class FilePath<T extends FileContentDto<?>> {

    private final Path path;
    private final Class<T> dtoClass;

    FilePath(Path path, Class<T> dtoClass) {
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
