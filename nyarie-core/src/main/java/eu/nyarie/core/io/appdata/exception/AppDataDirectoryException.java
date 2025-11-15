package eu.nyarie.core.io.appdata.exception;

import eu.nyarie.core.api.exception.NyarieException;

import java.io.FileNotFoundException;

public class AppDataDirectoryException extends NyarieException {

    private static final String PATH_IS_NO_DIRECTORY = "The configured path is no directory: %s";
    private static final String CONFIGURED_PATH_NOT_ABSOLUTE = "The configured app data directory must be absolute - was: %s";
    private static final String CONFIGURED_DIRECTORY_NOT_FOUND = "The configured app data directory could not be found: %s";

    protected AppDataDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppDataDirectoryException(String message) {
        super(message);
    }

    public static AppDataDirectoryException pathIsNoDirectory(String path) {
        return new AppDataDirectoryException(PATH_IS_NO_DIRECTORY.formatted(path));
    }

    public static AppDataDirectoryException configuredPathNotAbsolute(String path) {
        return new AppDataDirectoryException(CONFIGURED_PATH_NOT_ABSOLUTE.formatted(path));
    }

    public static AppDataDirectoryException directoryNotFound(String directory, FileNotFoundException cause) {
        return new AppDataDirectoryException(CONFIGURED_DIRECTORY_NOT_FOUND.formatted(directory), cause);
    }
}
