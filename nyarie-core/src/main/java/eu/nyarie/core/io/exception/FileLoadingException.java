package eu.nyarie.core.io.exception;

import java.nio.file.Path;

public class FileLoadingException extends IOException {

    private static final String INVALID_STRUCTURE = "Encountered invalid structure while reading file: %s";
    private static final String UNEXPECTED_ERROR_READING_FILE = "An unexpected error occurred during reading of file '%s': %s";
    private static final String UNEXPECTED_ERROR_CREATING_JSON_LOG = "An unexpected error occurred while trying to get the error location in faulty JSON";

    protected FileLoadingException(String message) {
        super(message);
    }
    protected FileLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FileLoadingException unexpectedErrorReadingFile(Path path, Throwable cause) {
        return new FileLoadingException(UNEXPECTED_ERROR_READING_FILE.formatted(path, cause.getMessage()), cause);
    }

    public static FileLoadingException unexpectedErrorWhileGettingJsonErrorLocation(Throwable cause) {
        return new FileLoadingException(UNEXPECTED_ERROR_CREATING_JSON_LOG, cause);
    }

    public static FileLoadingException invalidStructure(Path path, Throwable cause) {
        return new FileLoadingException(INVALID_STRUCTURE.formatted(path), cause);
    }
}
