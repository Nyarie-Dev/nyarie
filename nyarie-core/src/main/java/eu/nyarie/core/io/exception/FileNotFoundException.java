package eu.nyarie.core.io.exception;

import java.nio.file.Path;

public class FileNotFoundException extends IOException {

    private static final String REQUIRED_ASSET_FILE_NOT_FOUND = "Required asset file could not be found: %s";

    protected FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected FileNotFoundException(String message) {
        super(message);
    }
    
    public static FileNotFoundException requiredAssetNotFound(Path path) {
        return new FileNotFoundException(REQUIRED_ASSET_FILE_NOT_FOUND.formatted(path));
    }
}
