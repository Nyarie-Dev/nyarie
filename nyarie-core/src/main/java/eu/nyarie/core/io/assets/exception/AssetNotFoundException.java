package eu.nyarie.core.io.assets.exception;

import java.io.FileNotFoundException;

public class AssetNotFoundException extends AssetException {

    private static final String CONFIGURED_PATH_NOT_ABSOLUTE = "The configured asset directory must be absolute - was: %s";
    private static final String CONFIGURED_DIRECTORY_NOT_FOUND = "The configured asset directory could not be found: %s";

    protected AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AssetNotFoundException(String message) {
        super(message);
    }

    public static AssetNotFoundException configuredPathNotAbsolute(String path) {
        return new AssetNotFoundException(CONFIGURED_PATH_NOT_ABSOLUTE.formatted(path));
    }

    public static AssetNotFoundException assetDirectoryNotFound(String directory, FileNotFoundException cause) {
        return new AssetNotFoundException(CONFIGURED_DIRECTORY_NOT_FOUND.formatted(directory), cause);
    }
}
