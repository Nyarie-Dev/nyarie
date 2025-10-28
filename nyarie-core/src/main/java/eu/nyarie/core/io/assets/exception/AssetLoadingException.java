package eu.nyarie.core.io.assets.exception;

import java.nio.file.Path;

public class AssetLoadingException extends AssetException {

    private static final String INVALID_STRUCTURE = "Encountered invalid structure while reading asset file: %s";
    private static final String UNEXPECTED_ERROR_READING_FILE = "An unexpected error occurred during reading of asset file '%s': %s";

    protected AssetLoadingException(String message) {
        super(message);
    }
    protected AssetLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AssetLoadingException unexpectedErrorReadingFile(Path path, Throwable cause) {
        return new AssetLoadingException(UNEXPECTED_ERROR_READING_FILE.formatted(path, cause.getMessage()), cause);
    }

    public static AssetLoadingException invalidStructure(Path path, Throwable cause) {
        return new AssetLoadingException(INVALID_STRUCTURE.formatted(path), cause);
    }
}
